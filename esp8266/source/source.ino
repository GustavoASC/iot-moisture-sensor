#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>

#define MAX_VALUE_MOISTURE_SENSOR 1024.00
#define ENDPOINT_FUNCTION "192.168.0.111"
#define ENDPOINT_PORT 8080
#define SSID_REDE ""
#define SENHA_REDE ""
#define MINUTES_WAIT_BETWEEN_CHECKS 30

// Function (URL) to handle moisture value
const char *serverName = "http://192.168.0.111:8080/function/sensorhandler";

WiFiClient wifiClient;

// Prototypes
void handleMoisture(float moisture);
float readMoisture(void);
void connectWiFi(void);

void connectWiFi(void)
{
  Serial.println("Connecting to WiFi...");
  Serial.println();
  delay(1000);
  WiFi.begin(SSID_REDE, SENHA_REDE);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println("");
  Serial.println("WiFi connected!");
  Serial.println("Current IP address: ");
  Serial.println(WiFi.localIP());

  delay(1000);
}

float readMoisture(void)
{
  int adcValue;
  float moisturePercentage;

  adcValue = analogRead(0);
  Serial.print("[ADC value] ");
  Serial.println(adcValue);

  moisturePercentage = (100.00 - ((adcValue / MAX_VALUE_MOISTURE_SENSOR) * 100.00));
  Serial.print("[Moisture percentage] ");
  Serial.print(moisturePercentage);
  Serial.println("%");

  return moisturePercentage;
}

void handleMoisture(float moisture)
{
  if (WiFi.status() == WL_CONNECTED) {
    HTTPClient http;

    http.begin(wifiClient, serverName);
    http.addHeader("Content-Type", "text/plain");

    String httpRequestData = "{ \"moisture\": " + String(moisture) + "}";
    Serial.println("Request data: " + httpRequestData);
    int httpResponseCode = http.POST(httpRequestData);

    Serial.print("HTTP Response code: ");
    Serial.println(httpResponseCode);

    http.end();
  } else {
    Serial.println("WiFi Disconnected");
  }
}

void setup()
{
  Serial.begin(9600);
  connectWiFi();
  delay(2000);
}

void loop()
{
  float currentMoisture;
  char moistureCharArray[11];

  currentMoisture = readMoisture();

  Serial.println("Current value");
  sprintf(moistureCharArray, "field1=%d", currentMoisture);

  handleMoisture(currentMoisture);
  for (int i = 0; i < MINUTES_WAIT_BETWEEN_CHECKS; i++) {
    delay(60000);
  }
}
