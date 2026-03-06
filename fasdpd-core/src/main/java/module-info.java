module fasdpd.core {
    requires java.desktop;

    exports degeneration;
    exports fasdpd;
    exports fastaIO;
    exports filters;
    exports filters.primerpair;
    exports filters.singlePrimer;
    exports filters.validator;
    exports sequences;
    exports sequences.alignment;
    exports sequences.alignment.htmlproducer;
    exports sequences.dna;
    exports sequences.protein;
    exports sequences.util.compare;
    exports sequences.util.gccontent;
    exports sequences.util.santaLuciaEnergeticParameters;
    exports sequences.util.tmcalculator;
}
