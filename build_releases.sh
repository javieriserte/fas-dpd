mkdir -p releases
mkdir -p releases/gui
cp bin/* releases/gui
unzip lib/cmdGetArg_2.1.2.jar "cmdGA2/*" -d releases/gui/