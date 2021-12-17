# Modifies version and dates in some files before pushing to master

ver=$(cat VERSION)

update_help() {
    gawk '{
        if (NR==2)
            print "Version '$ver'"
        else if (NR==10) {
            print "    java -jar fasdpd.'$ver'.cli.jar fasdpd.FASDPD 'Commands'";
        } else {
            print $0
        }
    }' src/fasdpd/help > src/fasdpd/help.tmp
    mv src/fasdpd/help.tmp src/fasdpd/help
}

update_readme() {
    gawk '{
        if (NR==1)
            print "# FAS-DPD - Family Specific Degenerate Primer Design tool Version '$ver'"
        else if (NR==3) {
            print "Date: '$(date +%Y-%m-%d)'";
        } else {
            print $0
        }
    }' Readme.md > Readme.md.tmp
    mv Readme.md.tmp Readme.md
}

update_manual() {
    gawk '{
        if (NR==2)
            print "Version '$ver'"
        else if (NR==10) {
            print "    java -jar fasdpd.'$ver'.cli.jar fasdpd.FASDPD 'Commands'";
        } else {
            print $0
        }
    }' MANUAL.md > MANUAL.md.tmp
    mv MANUAL.md.tmp MANUAL.md
}

update_run_bat() {
    gawk '{
        if (NR==1)
            print "java -jar fasdpd.'$ver'.cli.jar ^"
        else {
            print $0
        }
    }' example/Run.bat > example/Run.bat.tmp
    mv example/Run.bat.tmp example/Run.bat
}

update_run_sh() {
    gawk '{
        if (NR==2)
            print "java -jar fasdpd.'$ver'.cli.jar \\"
        else {
            print $0
        }
    }' example/Run.sh > example/Run.sh.tmp
    mv example/Run.sh.tmp example/Run.sh
}

update_help
update_readme
update_manual
update_run_bat
update_run_sh