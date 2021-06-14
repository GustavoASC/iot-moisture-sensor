# Moisture sensor with serverless Computing

This repo represents an IoT application to detect the soil moisture level, sending an email when the level is below the minimum. The main components were developed with serverless functions, making use of the OpenFaaS platform. This way, components are developed as stateless functions which communicate with each other through HTTP. The following pictures show the final hardware assembly, composed of: (a) ESP8266 NodeMCU, and (b) soil moisture sensor.

<p align="center">
   <img src=https://user-images.githubusercontent.com/10179520/121828097-f0ac9380-cc94-11eb-8278-11830fa92bbf.png>
</p>
  
## Technologies
Several technologies were used on this project. MongoDB was used to store the global settings, such as minimum moisture level and email address to be notified when current moisture is below the minimum. HTML/CSS/JS were used to build the UI. Java and Python were used to develop the serverless functions. Finally, OpenFaaS and Docker were used to run the previously developed functions.

The code for ESP8266 was built with Arduino IDE.

![image](https://user-images.githubusercontent.com/10179520/121827919-42a0e980-cc94-11eb-863b-92c62571248f.png)

## Frontend

The frontend is a simple Web UI made with Bootstrap. When one clicks on Submit button, the _sensorsettings_ function is fired with HTTP POST method in order to update the global settings.

<p align="center">
   <img src=https://user-images.githubusercontent.com/10179520/121828333-abd52c80-cc95-11eb-950f-6dcdf06016e9.png>
</p>
