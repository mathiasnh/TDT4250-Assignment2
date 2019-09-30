# TDT4250-Assignment2
Second assignment for TDT4250 Advanced Software Design

## Structure
This project provides modularity in the form of multiple bundles that work together with minimal dependencies, and exstensibility in that the project easliy can be expanded with more bundles and components.

Project bundles:
* api: core conversion functionality
* gogo: shell commands 
* servlet: servlet providing the unit conversion web service

To run the project, just open `launch.bndrun`in the `tdt4250.conversion.servlet`bundle, and click Run OSGi.

# Gogo shell commands 
* `list` - lists all available unit conversions
* `add {name} {target unit} {source unit} {expression}` - adds a conversion with given attributes. For instance, Celcius to Fahrenheit would be added like `add celToFahr F C C*1.8+32`
* `remove {name}`- removes the given conversion
* `convert {source unit} {source value} {target unit}` - finds a conversion that matches the units and calculates the target value

## Problems 
* It doesn't really work... yet!
* The activator that is supposed to provide the functionality for the gogo shell does not seem to start any instances. I am not sure if this is the problem, but when attempting to add convesions I just get a nullPointerException.
* No input requirements (fault detection) for shell commands
