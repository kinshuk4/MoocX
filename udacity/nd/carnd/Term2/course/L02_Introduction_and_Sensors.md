### Introduction
Outcome of this module:
- Implement sensor fusion algorithm to track pedestrian relative to a car by fusing lidar and radar using a Kalman filter.

Sensor fusion needs to happen quickly: need to use high-performance language (C++) instead of Python.

### Sensors on a self-driving car

- Two stereo cameras that act like eyes
- Traffic signals may be on other side of intersection -> use special lens to give camera range to detect

#### Radar
Stands for 'radio detection and ranging'.

- sits behind front bumpers
- Uses Doppler effect (change in frequency of radar waves) to measure velocity directly.
- Gives us velocity as an independent parameter and allows sensor fusion algorithm to converge faster.
- Generates radar maps to help with localisation.
  - Provides measurements to objects without direct line of sight. Approx 150 deg field of vision, 200m range.
  - Resolution in vertical direction limited.
  - Radar clutter: Reflection across static objects e.g. soda cans on the street or manhole covers can cause problems. -> disregard static objects.

#### Lidar

Stands for 'light detection and ranging'.

- Infrared laser beam to det distance.
- Lasers pulsed, pulses reflected by objects. Reflections return a point cloud that represents these objects.
- Relies on difference between two or more scans to calculate velocity (as opposed to measuring it directly as with radars)
- 900nm wavelength range, some use longer wavelengths -> better in rain or fog.