#include "string.h"

const int CSn = 4; // Chip select
const int CLK = 7; // Clock signal
const int FLDO = 8; // Digital Output from the encoder which delivers me a 0 or 1, depending on the bar angle
const int FRDO = 9; // Digital Output from the encoder which delivers me a 0 or 1, depending on the bar angle
const int BLDO = 10; // Digital Output from the encoder which delivers me a 0 or 1, depending on the bar angle
const int BRDO = 11; // Digital Output from the encoder which delivers me a 0 or 1, depending on the bar angle

String sensor;

void setup(){
  Serial.begin(115200);

  pinMode(CSn, OUTPUT);
  pinMode(CLK, OUTPUT);
  pinMode(FLDO, INPUT);
  pinMode(FRDO, INPUT);
  pinMode(BLDO, INPUT);
  pinMode(BRDO, INPUT);

  digitalWrite(CLK, HIGH);
  digitalWrite(CSn, HIGH);
}

void loop() {
  

  sensor = readSensor(); 
  Serial.println(sensor);
  
}


int convert(long long n) {
  int dec = 0, i = 0, rem;

  while (n!=0) {
    rem = n % 10;
    n /= 10;
    dec += rem * pow(2, i);
    ++i;
  }

  return dec;
}

String readSensor(){
  unsigned int fldataOut = 0;
  unsigned int frdataOut = 0;
  unsigned int bldataOut = 0;
  unsigned int brdataOut = 0;

  digitalWrite(CSn, LOW);
  delayMicroseconds(1); //Waiting for Tclkfe

  //Passing 12 times, from 0 to 11
  for(int x=0; x<12; x++){
    digitalWrite(CLK, LOW);
    delayMicroseconds(1); //Tclk/2
    digitalWrite(CLK, HIGH);
    delayMicroseconds(1); //Tdo valid, like Tclk/2
    fldataOut = (fldataOut << 1) | digitalRead(FLDO); //shift all the entering data to the left and past the pin state to it. 1e bit is MSB
    frdataOut = (frdataOut << 1) | digitalRead(FRDO); //shift all the entering data to the left and past the pin state to it. 1e bit is MSB
    bldataOut = (bldataOut << 1) | digitalRead(BLDO); //shift all the entering data to the left and past the pin state to it. 1e bit is MSB
    brdataOut = (brdataOut << 1) | digitalRead(BRDO); //shift all the entering data to the left and past the pin state to it. 1e bit is MSB
  }

  fldataOut = convert(fldataOut);
  frdataOut = convert(frdataOut);
  bldataOut = convert(bldataOut);
  brdataOut = convert(brdataOut);


  digitalWrite(CSn, HIGH); //deselects the encoder from reading

  String dataOut = ":" + String(fldataOut) + "," + String(frdataOut) + "," + String(bldataOut) + "," + String(brdataOut);
  return dataOut;
}
