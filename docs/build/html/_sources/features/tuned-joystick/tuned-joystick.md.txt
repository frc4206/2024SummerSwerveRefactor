
# Tuned Joystick

<hr>

## Introduction

What is the most important part of an FRC team?  There are so many key aspects that there is no single answer.  However, most people would agree that the driver(s) plays a vital role in a team's success.  It is _essential_ that the driver is able to control his/her robot with maximum precision.  _This can not be stressed enough_.  What can we do with software to aid the driver?

Often overlooked yet glaringly obvious is also what is right in your hands: the controller joysticks.

Manipulating joystick inputs effectively can:

- Prevent driver overshoot
- Enhance precision control
- Customize the 'feel' for the user

<hr>

## Deadzones

Every controller is an imperfect device.  Due to hardware imperfections at the microscopic scale, when a joystick returns to the neutral position, the joystick _more than likely_ does not map to exactly `0.0` input.  So, it is common to have a 'deadzone' (also called a 'deadband'), where any input less than the deadzone is mapped to zero input.

<br>

<div style="text-align:center">

![Deadzone](./deadzone.png)
</div>

<br>

The above has a deadzone of 20% (`0.2`).  The area highlighted in yellow is the input where precision control is lost due to the dead-zone.  This poses an issue; the user is limited to a minimum input of 20%! This causes driver overshoot, and in general inhibits the user's ability to make fine precision adjustments that are critical for game-piece acquisition or scoring opportunities. 

What we really want is something more like this:

<div style="text-align:center">

![Mapped Deadzone](./mapped-deadzone.png)
</div>

<br>

This requires some linear interpolation.  **battleaid**'s `TunedJoystick` will do that for you.

<hr>

## Response Curves

Response curves are functions that will vary output magnitude based on a linear input.  Response curves allow one to customize the 'feel' of a joystick. `TunedJoystick` only implements a few response exponential response curves.

> <span style="color:red">Linear -> y = x</span>.

<div style="text-align:center">

![Response Curves](./response-curves.png)
</div>

The quadratic formula is given by:

This is a test. Here is an equation:
:math:`X_{0:5} = (X_0, X_1, X_2, X_3, X_4)`.
Here is another:

.. math::
    :label: This is a label

    \nabla^2 f =
    \frac{1}{r^2} \frac{\partial}{\partial r}
    \left( r^2 \frac{\partial f}{\partial r} \right) +
    \frac{1}{r^2 \sin \theta} \frac{\partial f}{\partial \theta}
    \left( \sin \theta \, \frac{\partial f}{\partial \theta} \right) +
    \frac{1}{r^2 \sin^2\theta} \frac{\partial^2 f}{\partial \phi^2}

<hr>
