# FAS-DPD - Family Specific Degenerate Primer Design tool Version 1.4.3

Date: 2021-12-17

Released under the terms of the General Public License Version 3 (GPL v3)
See the file LICENCE for details.

## General

FAS-DPD core searching and scoring algorithms is working correctly.
Several features will be added in future versions.

# Authors

FAS-DPD project, including algorithms design, software implementation
and experimental laboratory work, is being developed as a part of the
Research Program:

- **"Microbiología molecular básica y aplicaciones biotecnológicas"** (
    Basic Molecular Microbiology and biotechnological applications)

And is being conducted at:

 - LIGBCM: Laboratorio de Ingeniería Genética y Biología Celular y Molecular.
   (Laboratory of Genetic Engineering and Cellular and Molecular Biology)
   Universidad Nacional de Quilmes.(National University Of Quilmes) Quilmes,
   Buenos Aires, Argentina.

The complete team for this project is formed by:

 - ph.D. Javier A. Iserte.
 - ph.D. Betina I. Stephan.
 - ph.D. Sandra E. Goñi.
 - ph.D. P. Daniel Ghiringhelli.
 - ph.D. Mario E. Lozano.

### Corresponding Authors

- Javier A. Iserte. <jiserte@unq.edu.ar>
- Mario E. Lozano. <mlozano@unq.edu.ar>

## Requisites

- Java JRE 15.
- JUnit 5 - http://sourceforge.net/projects/junit.

## Running FAS-DPD

FAS-DPD is executable from command line.
See 'examples' folder and Documentations for details.

## Documentation

Api documentation produced with javadoc is found in 'doc' folder.
Manual with instructions to use FAS-DPD are in file: 'MANUAL'

## Package contents

    /src/         : Code source files.
    /examples/    : Example showing how to use FAS-DPD.
    /doc/         : API documentation.
    /lib/         : Contains cmdGetArg_2.1.2.jar - Library for command line
                    parsing.
    StandardCode  : File containing the standard genetic code used in FAS-DPD.
    LICENCE       : GPL v3 license terms.
    CHANGELOG     : Log with version modifications.
    VERSION       : Version.
    MANUAL.md     : FAS-DPD manual.
    Readme.md     : This file.

## Bug report

Please send a mail to Javier Iserte <jiserte@unq.edu.ar> including thedetails of
bugs.

## Setting up development enviroment.

### JDK

You will need the JDK-15.

### Dependencies

This is a small project with unmanaged dependencies.
There are two dependencies required for this project.

- cmdGetArg library: It comes included within the source code.
- Junit 5: You can download junit-platform-console-standalone-1.8.2.jar from
    maven central and put it in the lib folder. Then update .classpath file.
