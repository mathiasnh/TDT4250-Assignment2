# TDT4250-Assignment2
Second assignment for TDT4250 Advanced Software Design

## Structure
This project provides modularity in the form of multiple bundles that work together with low coupling, and exstensibility in that the project is cohesive and easliy expanded with more bundles and components.

Project bundles:
* api: core conversion functionality
* gogo: shell commands 
* servlet: servlet providing the unit conversion web service

To run the project, just open `launch.bndrun`in the `tdt4250.conversion.servlet`bundle, and click Run OSGi.

# Gogo shell commands 
* `list` - lists all available unit conversions
* `add {name} {target unit} {source unit} {expression}` - adds a conversion with given attributes. For instance, Celcius to Fahrenheit would be added like `add celToFahr F C "*1.8+32"`. Keep in mind that 
* `remove {name}`- removes the given conversion
* `convert {source unit} {source value} {target unit}` - finds a conversion that matches the units and calculates the target value

## Problems 
* Pre-made unit conversions are not loaded
* No input requirements (fault detection) for shell commands
