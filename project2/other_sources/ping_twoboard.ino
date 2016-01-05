const int pingPin = 7;

void setup() {
  Serial.begin(9600);
}

void loop() {
  long duration, inches, cm;
  
  pinSetup();
  
  duration = pulseIn(pingPin, HIGH);
  
  cm = microsecondsToCentimeters(duration);
  
  if (30 < cm && cm < 120) {
    Serial.print(1);    
  }
  else {
    Serial.print(0);
  }
  
  delay(100);
}

void pinSetup() {
  pinMode(pingPin, OUTPUT);
  digitalWrite(pingPin, LOW);
  delayMicroseconds(2);
  digitalWrite(pingPin, HIGH);
  delayMicroseconds(5);
  digitalWrite(pingPin, LOW);

  pinMode(pingPin, INPUT);
}

long microsecondsToInches(long microseconds) {
  return microseconds / 74 / 2;
}

long microsecondsToCentimeters(long microseconds) {
  return microseconds / 29 / 2;
}
