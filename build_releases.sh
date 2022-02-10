ver=$(cat VERSION)

clean_release_folder() {
    rm -Rf releases
    mkdir -p releases
    mkdir -p releases/gui/jar
    mkdir -p releases/cli/jar
}

add_files_to_release_gui() {
    cp bin/* releases/gui/jar -R
    cp StandardCode releases/gui/
    cp VERSION releases/gui/
    cp MANUAL.md releases/gui/
    cp LICENSE releases/gui/
    cp Readme.md releases/gui/
    unzip lib/cmdGetArg_2.1.2.jar "cmdGA2/*" "cmdGA/*" "cmdGA2/**/*" "cmdGA/**/*" -d releases/gui/jar
    echo "java -jar fasdpd.$ver.gui.jar ""$""@" > releases/gui/launch.sh
    echo "java -jar fasdpd.$ver.gui.jar %*" > releases/gui/launch.bat
}

add_files_to_release_cli() {
    cp bin/* releases/cli/jar -R
    cp example releases/cli/ -R
    cp StandardCode releases/cli/
    cp VERSION releases/cli/
    cp MANUAL.md releases/cli/
    cp LICENSE releases/cli/
    cp Readme.md releases/cli/
    unzip lib/cmdGetArg_2.1.2.jar "cmdGA2/*" "cmdGA/*" "cmdGA2/**/*" "cmdGA/**/*" -d releases/cli/jar
    echo "java -jar fasdpd.$ver.cli.jar ""$""@" > releases/cli/launch.sh
    echo "java -jar fasdpd.$ver.cli.jar %*" > releases/cli/launch.bat
}

build_gui_release() {
    jar cfe releases/gui/fasdpd.$ver.gui.jar fasdpd.UI.v1.MainFASDPD \
        -C releases/gui/jar .
    zip -j -r releases/gui/fasdpd.$ver.gui.zip releases/gui/fasdpd.$ver.gui.jar \
        releases/gui/StandardCode \
        releases/gui/VERSION \
        releases/gui/LICENSE \
        releases/gui/launch.sh \
        releases/gui/launch.bat \
        releases/gui/Readme.md
    mv releases/gui/fasdpd.$ver.gui.zip .
}

build_cli_release() {
    jar cfe releases/cli/fasdpd.$ver.cli.jar fasdpd.FASDPD \
        -C releases/cli/jar .
    zip -j -r releases/cli/example.zip releases/cli/example
    zip -j -r releases/cli/fasdpd.$ver.cli.zip releases/cli/fasdpd.$ver.cli.jar \
        releases/cli/StandardCode \
        releases/cli/VERSION \
        releases/cli/LICENSE \
        releases/cli/MANUAL.md \
        releases/cli/Readme.md \
        releases/cli/launch.sh \
        releases/cli/launch.bat \
        releases/cli/example.zip
    mv releases/cli/fasdpd.$ver.cli.zip .
}

remove_release_folder() {
    rm -Rf releases/*
}

move_files_to_release_folder() {
    mv fasdpd.$ver.gui.zip releases/
    mv fasdpd.$ver.cli.zip releases/
}

clean_release_folder
add_files_to_release_gui
build_gui_release

add_files_to_release_cli
build_cli_release

remove_release_folder
move_files_to_release_folder
