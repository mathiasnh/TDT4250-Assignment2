# TDT4250-Assignment2
Second assignment for TDT4250 Advanced Software Design

## Structure
This project provides modularity in the form of multiple bundles that work together with low coupling, and exstensibility in that the project is cohesive and easliy expanded with more bundles and components.

Project bundles:
* [api](tdt4250.conversion.api): core conversion functionality
* [gogo](tdt4250.conversion.gogo): shell commands 
* [servlet](tdt4250.conversion.api): servlet providing the unit conversion web service

## Usage

To run the project, just open `launch.bndrun`in the `tdt4250.conversion.servlet`bundle, and click Run OSGi.

### HTTP Service
When an OSGi Framewrok has been launched, the servlet is run on port `8080` and is used like this: `http://localhost:8080/conversion?src=C&val=20&tar=F`.
If a unit converter is found with appropriate source and target values, the conversion will be calculated and displayed.

### Gogo shell commands 
* `list` - lists all available unit conversions
* `add {name} {target unit} {source unit} {expression}` - adds a conversion with given attributes. For instance, Celcius to Fahrenheit would be added like `add celToFahr F C "*1.8+32"`. Keep in mind that using the multiplication operator (`*`) will cause an error, so make sure to surround it the expression with quotation marks if you wish to use multiplication.
* `remove {name}`- removes the given conversion
* `convert {source unit} {source value} {target unit}` - finds a conversion that matches the units and calculates the target value

## Problems 
* Pre-made unit conversions are not loaded correctly
