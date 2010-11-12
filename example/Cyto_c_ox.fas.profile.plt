set terminal postscript eps font "Helvetica,20"
set output "Cyto_c_ox.fas.profile.ps"
set yrange [0:21.599999999999998]
plot 'Cyto_c_ox.fas.profile' with filledcurves below notitle lc rgb "#000000"
set terminal png font verdana 10 size 1024,768
set output "Cyto_c_ox.fas.profile.png"
replot