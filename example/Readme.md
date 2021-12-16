# This is an example of use of FAS-DPD.

The file 'Cyto_c_ox.fas' contains an alignment of several nucleotide sequences
of Cytochrome C Oxidase enzyme from different organisms.

To run this example, download the latest release of the command line version
from github, and copy the jar file in the 'example' folder. The name is this
file is like fasdpd.x.y.z.cli.jar, where x.y.z is the version number.

The file 'Run.bat' is a batch file with the options of execution.
You may need to edit the file and replace the name of the jar file with the
correct version number.

	java -jar fasdpd.x.y.z.cli.jar    // FAS-DPD is written in Java. These
									  // option are for Java Virtual Machine,
									  // not FAS-DPD.
	/INFIlE: "Cyto_c_ox.fas"          // Read source alignment from
	                                  // "Cyto_c_ox.fas" file.
	/OUTFILE: "Cyto_c_ox.fas.primers" // Write selected primers to
	                                  // "Cyto_c_ox.fas.primers".
	/GCFILE: "StandardCode"           // Read Genetic Code from "StandardCode"
	                                  // file. Althought is not used when the
									  // source	alignment is a DNA alignment,
									  // it is always required.
	/isDNA                            // Treat the alignment as DNA.
	/FDEG                             // Filter Primers that ends with a
	                                  // degenerated base.
	/Frep                             // Filter Primers that have the last two
	                                  // bases repeated.
	/Q: 100                           // Retrieve the one hundred highest score
	                                  // primers.
	/profile: "Cyto_c_ox.fas.profile" // Creates a histogram of positions of
	                                  // resulting primers.
                                      // Histogram values are stored in
								      // "Cyto_c_ox.fas.profile".
                                      // Cyto_c_ox.fas.profile.plt is a simple
									  // script to view the histogram in
									  // GNU-PLOT
