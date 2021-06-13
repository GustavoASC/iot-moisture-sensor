import json
from pymongo import MongoClient
from urllib.parse import quote_plus

DB_NAME = 'openfaas'
DB_HOST_PORT = '192.168.0.110:27017'

# --------------------------------------------------------------
# Database methods
# --------------------------------------------------------------
class Database:

    def _get_uri(self):
        host = DB_HOST_PORT
        password = "admin"
        username = "admin"
        return "mongodb://%s:%s@%s" % (quote_plus(username), quote_plus(password), host)

    def _get_mongo_client(self):
        uri = self._get_uri()
        return MongoClient(uri)

    def get_settings_collection(self):
        client = self._get_mongo_client()
        db = client[DB_NAME]
        return db.sensorsettings

# --------------------------------------------------------------
# Settings crud impl
# --------------------------------------------------------------
class SettingsCrud:
    
    def __init__(self):
        self.db = Database()

    def _insert(self, col, settings):
        res = col.insert_one(settings)
        return "Record inserted: {}".format(res.inserted_id)

    def _update(self, col, settings):
        new_value = { "$set": settings}
        res = col.update_one({}, new_value)
        return "Record updated: {}".format(res.modified_count)

    def insert_update(self, email, min_moisture):
        col = self.db.get_settings_collection()
        settings = {
               "email": email,
               "min_moisture": min_moisture
        }
        existing_data = col.find_one()
        if existing_data == None:
            return self._insert(col, settings)
        else:
            return self._update(col, settings)
        
    def list(self):
        col = self.db.get_settings_collection()
        existing_data = col.find_one()
        if existing_data == None:
            return "{}"
        else:
            settings = {
                "email": existing_data["email"],
                "min_moisture": existing_data["min_moisture"]
            }
            return  json.dumps(settings)
        
# --------------------------------------------------------------
# Http methods impl
# --------------------------------------------------------------
class HttpImpl:

    def __init__(self, path, body):
        self.path = path
        self.body = body
        self.crud = SettingsCrud()

    def post(self):
        music = json.loads(self.body)
        res = self.crud.insert_update(music.get("email", ""),
                                      music.get("min_moisture", ""))
        return res

    def get(self):
        return self.crud.list()

# --------------------------------------------------------------
# Function handler
# --------------------------------------------------------------
def handle(event, context):

    try:
        method = event.method
        http = HttpImpl(event.path, event.body)

        result = ""
        if method == "POST":
            result = http.post()
        elif method == "GET":
            result = http.get()
        else:
            result = "Method: {} not supported".format(method)

        return {
            "statusCode": 200,
            "headers": {
                "Content-type": "text/plain; charset=utf-8",
                "content-length": len(result),
                "access-control-allow-origin": "*"
            },
            "body": result
        }
    except Exception as err:
        return str(err)

if __name__ == "__main__":
    print(handle("", ""))