#!/usr/bin/env bash
java -jar fasdpd.x.y.z.cli.jar \
    /infile:"Cyto_c_ox.fas" \
    /OUTFILE:"Cyto_c_ox.fas.primers" \
    /gcfile:"StandardCode" \
    /isDNA \
    /FDEG \
    /Frep \
    /Q: 100 \
    /profile:"Cyto_c_ox.fas.profile"