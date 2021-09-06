package fasdpd;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Vector;

import sequences.util.compare.DegeneratedDNAMatchingStrategy;
import sequences.util.tmcalculator.SantaluciaTmEstimator;
import sequences.util.tmcalculator.SimpleTmEstimator;
import sequences.util.tmcalculator.TmEstimator;
import cmdGA2.CommandLine;
import cmdGA2.NoArgumentOption;
import cmdGA2.SingleArgumentOption;
import cmdGA2.exceptions.IncorrectCommandLineException;
import cmdGA2.returnvalues.FloatValue;
import cmdGA2.returnvalues.IntegerValue;
import cmdGA2.returnvalues.StringValue;
import filters.primerpair.FilterAmpliconSize;
import filters.primerpair.FilterGCCompatibility;
import filters.primerpair.FilterHeteroDimer;
import filters.primerpair.FilterHeteroDimerFixed3;
import filters.primerpair.FilterMeltingTempCompatibility;
import filters.primerpair.FilterOverlapping;
import filters.primerpair.FilterSmallAmpliconSize;
import filters.singlePrimer.Filter5vs3Stability;
import filters.singlePrimer.FilterBaseRuns;
import filters.singlePrimer.FilterCGContent;
import filters.singlePrimer.FilterDegeneratedEnd;
import filters.singlePrimer.FilterHomoDimer;
import filters.singlePrimer.FilterHomoDimerFixed3;
import filters.singlePrimer.FilterMeltingPointTemperature;
import filters.singlePrimer.FilterPrimerScore;
import filters.singlePrimer.FilterRepeatedEnd;
import filters.validator.ValidateAlways;
import filters.validator.ValidateForFilterPrimerPair;
import filters.validator.ValidateForFilterSinglePrimer;
import filters.validator.Validate_AND;
import filters.validator.Validator;


public class SearchParameter {
	// parameters are stored as instance variables 
	private Optional<String> infile = Optional.empty();
	private String outfile;
	private String gcfile;
	private String profile="";
	private int lenMin=20;
	private int lenMax=25;
	private Validator filter= new ValidateAlways();
	private Validator filterpair= new ValidateAlways();
	private int quantity=20;
	private int startPoint=1; // The number of the first position is One, not Zero.
	private int endPoint=-1; // -1 represents the end the sequence.
	private boolean directStrand = true;
	private boolean isDNA = false;
	private float Nx = 1f;
	private float Ny = 1f;
	private float pA = 0f;
	private Set<StrandSearchDirection> strands;

	private boolean searchPair=false;
	private boolean useSantaLuciaToEstimateTm = true; // TODO MAY BE USELESS. 

    ////////////////
	// CONSTRUCTOR
	////////////////
	
	/**
	 * creates an empty SearchParameter object
	 */
	public SearchParameter() {
		super();
		strands = new HashSet<StrandSearchDirection>();
		strands.add(StrandSearchDirection.Forward);
	}

    /////////////////////
	// PUBLIC INTERFACE
	/////////////////////

	public Set<StrandSearchDirection> getStrands() {
		return this.strands;
	}

	public void addStrand(StrandSearchDirection strand) {
		this.strands.add(strand);
	}

	public void clearStrands() {
		this.strands.clear();
	}

	/**
	 * Looks for search parameters reading the command line options.
	 */
	// TODO decouple command line interpretation from SearchParameter.
	public void retrieveFromCommandLine(String[] args) throws InvalidCommandLineException {
	
		///////////////////////////////////
		// Create Command Line
		CommandLine cmd = new CommandLine();
		
		SingleArgumentOption<Integer> lenMin = new SingleArgumentOption<Integer>(cmd, "/lenMin", new IntegerValue(), 20);
		SingleArgumentOption<Integer> lenMax = new SingleArgumentOption<Integer>(cmd, "/lenMax", new IntegerValue(), 25);
		
		SingleArgumentOption<String> infile = new SingleArgumentOption<String>(cmd, "/infile", new StringValue(), null);
		SingleArgumentOption<String> outfile = new SingleArgumentOption<String>(cmd, "/outfile", new StringValue(), null);
		SingleArgumentOption<String> gcfile = new SingleArgumentOption<String>(cmd, "/gcfile", new StringValue(), null);
		SingleArgumentOption<String> profile = new SingleArgumentOption<String>(cmd, "/profile", new StringValue(), null);
		SingleArgumentOption<Integer> quantity = new SingleArgumentOption<Integer>(cmd, "/q", new IntegerValue(), 20);
		SingleArgumentOption<Integer> start = new SingleArgumentOption<Integer>(cmd, "/startingpoint", new IntegerValue(), 1);
		SingleArgumentOption<Integer> end = new SingleArgumentOption<Integer>(cmd, "/endpoint", new IntegerValue(), -1);

		SingleArgumentOption<Float> nx = new SingleArgumentOption<Float>(cmd, "/nx", new FloatValue(), (float)1);
		SingleArgumentOption<Float> ny = new SingleArgumentOption<Float>(cmd, "/ny", new FloatValue(), (float)1);
		SingleArgumentOption<Float> pa = new SingleArgumentOption<Float>(cmd, "/pa", new FloatValue(), (float)0);
		
		NoArgumentOption isDna = new NoArgumentOption(cmd, "/isDNA");
		NoArgumentOption isProtein = new NoArgumentOption(cmd, "/isProtein");
		NoArgumentOption complementary = new NoArgumentOption(cmd, "/ComplementaryStrand");

		NoArgumentOption pair = new NoArgumentOption(cmd, "/pair");
		NoArgumentOption tmSL = new NoArgumentOption(cmd, "/tmsantalucia");
		NoArgumentOption tmsimple = new NoArgumentOption(cmd, "/tmsimple");
		
		//////////////////
		// DEFINE OPTIONS FOR FILTERS
		//////////////////

		NoArgumentOption filterRep = new NoArgumentOption(cmd, "/frep");
		NoArgumentOption filterDeg = new NoArgumentOption(cmd, "/fdeg");

		SingleArgumentOption<Float[]> tmOpt = new SingleArgumentOption<Float[]>(cmd, "/tm", new FloatArrayValue(), new Float[]{50f,65f});
		NoArgumentOption notmOpt = new NoArgumentOption(cmd, "/notm");

		SingleArgumentOption<End5v3Value.Result> end5v3 = new SingleArgumentOption<End5v3Value.Result>(cmd, "/end5v3", new End5v3Value(), new End5v3Value.Result(1.5, 273 + 37, 0.05, 5));
		NoArgumentOption noend5v3 = new NoArgumentOption(cmd, "/noend5v3");
		
		SingleArgumentOption<Integer> baserun = new SingleArgumentOption<Integer>(cmd, "/baserun", new IntegerValue(), 4);
		NoArgumentOption nobaserun = new NoArgumentOption(cmd, "/nobaserun");

		SingleArgumentOption<Integer> homodimer = new SingleArgumentOption<Integer>(cmd, "/homodimer", new IntegerValue(), 5);
		NoArgumentOption nohomodimer = new NoArgumentOption(cmd, "/nohomodimer");

		SingleArgumentOption<Integer> homodimerfixedEnd = new SingleArgumentOption<Integer>(cmd, "/homodimer3", new IntegerValue(), 3);
		NoArgumentOption nohomodimerfixedEnd = new NoArgumentOption(cmd, "/nohomodimer3");

		SingleArgumentOption<Float[]> gccontent = new SingleArgumentOption<Float[]>(cmd, "/gc", new FloatArrayValue(), new Float[]{40f,60f});
		NoArgumentOption nogccontent = new NoArgumentOption(cmd, "/nogccontent");

		SingleArgumentOption<Float> score = new SingleArgumentOption<Float>(cmd, "/score", new FloatValue(), 0.8f);
		NoArgumentOption noscore = new NoArgumentOption(cmd, "/noscore");

		SingleArgumentOption<Integer> ampsize = new SingleArgumentOption<Integer>(cmd, "/size", new IntegerValue(), 200);
		NoArgumentOption noampsize = new NoArgumentOption(cmd, "/nosize");

		SingleArgumentOption<Integer> smallampsize = new SingleArgumentOption<Integer>(cmd, "/minsize", new IntegerValue(), 100);
		NoArgumentOption nosmallampsize = new NoArgumentOption(cmd, "/nominsize");

		SingleArgumentOption<Float> gccomp = new SingleArgumentOption<Float>(cmd, "/gccomp", new FloatValue(), 10f);
		NoArgumentOption nogccomp = new NoArgumentOption(cmd, "/nogccomp");
		
		SingleArgumentOption<Integer> heterodimer = new SingleArgumentOption<Integer>(cmd, "/hetdimer", new IntegerValue(), 5);
		NoArgumentOption noheterodimer = new NoArgumentOption(cmd, "/nohetdimer");
		
		SingleArgumentOption<Integer> heterodimerfixedEnd = new SingleArgumentOption<Integer>(cmd, "/hetdimer3", new IntegerValue(), 3);
		NoArgumentOption noheterodimerfixedEnd = new NoArgumentOption(cmd, "/nohetdimer3");

		SingleArgumentOption<Float> tmcomp = new SingleArgumentOption<Float>(cmd, "/tmcomp", new FloatValue(), 5f);
		NoArgumentOption notmcomp = new NoArgumentOption(cmd, "/notmcomp");

//		Parser parser = new Parser();
	
		//////////////////
		// DEFINE OPTIONS
		//////////////////
		
		
//		SingleOption lenMin = new SingleOption(parser, 20, "/lenMin", IntegerParameter.getParameter());
//		SingleOption lenMax = new SingleOption(parser, 25, "/lenMax", IntegerParameter.getParameter());
//		
//		SingleOption infile = new SingleOption(parser, null , "/infile", StringParameter.getParameter());
//		SingleOption outfile = new SingleOption(parser, null , "/outfile", StringParameter.getParameter());
//		SingleOption gcfile = new SingleOption(parser, null , "/gcfile", StringParameter.getParameter());
//		SingleOption profile = new SingleOption(parser, null , "/profile", StringParameter.getParameter());
//		SingleOption quantity = new SingleOption(parser, 20 , "/q", IntegerParameter.getParameter());
//		SingleOption start = new SingleOption(parser, 1 , "/startingpoint", IntegerParameter.getParameter());
//		SingleOption end = new SingleOption(parser, -1 , "/endpoint", IntegerParameter.getParameter());
//
//		SingleOption nx = new SingleOption(parser, (float)1 , "/nx", FloatParameter.getParameter());
//		SingleOption ny = new SingleOption(parser, (float)1 , "/ny", FloatParameter.getParameter());
//		SingleOption pa = new SingleOption(parser, (float)0 , "/pa", FloatParameter.getParameter());
//		
//		NoOption isDna = new NoOption(parser, true , "/isDNA");
//		NoOption isProtein = new NoOption(parser, false , "/isProtein");
//		
//		NoOption complementary = new NoOption(parser, false , "/ComplementaryStrand");
//		
//		NoOption pair = new NoOption(parser, false, "/pair");
//		
//		NoOption tmSL = new NoOption(parser, true, "/tmsantalucia");
//		NoOption tmsimple = new NoOption(parser, false, "/tmsimple"); 
//		
//		//////////////////
//		// DEFINE OPTIONS FOR FILTERS
//		//////////////////
//		
//		NoOption filterRep = new NoOption(parser, false , "/frep");
//		NoOption filterDeg = new NoOption(parser, false , "/fdeg");
//		
//		SingleOption tmOpt = new SingleOption(parser, new Float[]{50f,65f}, "/tm", FloatArrayParameter.getParameter());
//		NoOption notmOpt = new NoOption(parser, false, "/notm");
//		
//		SingleOption end5v3 = new SingleOption(parser, new End5v3ParameterType.Result(1.5, 273 + 37, 0.05, 5), "/end5v3", End5v3ParameterType.getParameter()); 
//		NoOption noend5v3 = new NoOption(parser, false, "/noend5v3");
//		
//		SingleOption baserun = new SingleOption(parser, 4,"/baserun", IntegerParameter.getParameter());
//		NoOption nobaserun = new NoOption(parser, false, "/nobaserun");
//
//		SingleOption homodimer = new SingleOption(parser, 5 , "/homodimer", IntegerParameter.getParameter());
//		NoOption nohomodimer= new NoOption(parser, false, "/nohomodimer");		
//		
//		SingleOption homodimerfixedEnd = new SingleOption(parser, 3 , "/homodimer3", IntegerParameter.getParameter());
//		NoOption nohomodimerfixedEnd= new NoOption(parser, false, "/nohomodimer3");		
//		
//		SingleOption gccontent = new SingleOption(parser, new Float[]{40f,60f}, "/gc", FloatArrayParameter.getParameter());
//		NoOption nogccontent = new NoOption(parser, false, "/nogccontent");	
//		
//		SingleOption score = new SingleOption(parser, 0.8d, "/score", FloatParameter.getParameter());
//		NoOption noscore = new NoOption(parser, false, "/noscore");	
//
//		SingleOption ampsize = new SingleOption(parser, 200, "/size", IntegerParameter.getParameter());
//		NoOption noampsize = new NoOption(parser, false, "/nosize");
//		
//		SingleOption smallampsize = new SingleOption(parser, 100, "/minsize", IntegerParameter.getParameter());
//		NoOption nosmallampsize = new NoOption(parser, false, "/nominsize");		
//		
//		SingleOption gccomp = new SingleOption(parser, 10f, "/gccomp", FloatParameter.getParameter());
//		NoOption nogccomp= new NoOption(parser, false, "/nogccomp");		
//		
//		SingleOption heterodimer = new SingleOption(parser, 5 , "/hetdimer", IntegerParameter.getParameter());
//		NoOption noheterodimer= new NoOption(parser, false, "/nohetdimer");		
//		
//		SingleOption heterodimerfixedEnd = new SingleOption(parser, 3 , "/hetdimer3", IntegerParameter.getParameter());
//		NoOption noheterodimerfixedEnd= new NoOption(parser, false, "/nohetdimer3");		
//		
//		SingleOption tmcomp = new SingleOption(parser, 5d, "/tmcomp", FloatParameter.getParameter());
//		NoOption notmcomp = new NoOption(parser, false, "/notmcomp");		
		
		
		
		//////////////////////////
		// READ COMMAND LINE
		/////////////////////////
		
//		try {
//			
//			parser.parseEx(args);
//			
//		} catch (IncorrectParameterTypeException e) {
//
//			System.err.println("There was an error trying to parse the command line:");
//			
//			System.err.println(e.getMessage());
//			
//			System.exit(1);
//			
//		}
		try {
			cmd.read(args);
		} catch (IncorrectCommandLineException e) {
			System.err.println("There was an error trying to parse the command line:");
			System.err.println(e.getMessage());
			System.exit(1);
		}
		/////////////////////////////
		// CHECK COMMAND LINE SYNTAX
		/////////////////////////////

		if (! (infile.isPresent()&&outfile.isPresent()&&gcfile.isPresent())) {
			// infile, outfile and gcfile are required!
			// if one of them is not present then the command line is not well formed.
			throw new InvalidCommandLineException("Infile, Outfile and GCfile are required arguments");
		}
		
		if (isDna.isPresent()&&isProtein.isPresent()) {
			// both option can not be in the command line at the same moment.
			throw new InvalidCommandLineException("isDna option and isProtein Option can not be in the command line at the same time.");
		}

		if (tmSL.isPresent() && tmsimple.isPresent() ) {
			// These options can not be in the commandline at the same time.
			throw new InvalidCommandLineException("Can not use both Tm estimators at the same time. Choose one.");
		}

		if((Integer)lenMin.getValue() > (Integer)lenMax.getValue() ) {
			// Primer Min can not be greater than Primer Max.
			throw new InvalidCommandLineException("Max length is lesser than Min length");
		}
		
		if (tmOpt.isPresent() && notmOpt.isPresent() ) { 
 			// This options can't be in the command line at the same time 
 			throw new InvalidCommandLineException("/tm and /notm options can not appear in the command line simoultaneously");
		}
		if (end5v3.isPresent() && noend5v3.isPresent() ) { 
 			// This options can't be in the command line at the same time
 			throw new InvalidCommandLineException("/end5v3 and /noend5v3 options can not appear in the command line simoultaneously");
		}
		if (baserun.isPresent() && nobaserun.isPresent() ) { 
 			// This options can't be in the command line at the same time
 			throw new InvalidCommandLineException("/baserun and /nobaserun options can not appear in the command line simoultaneously");
		}
		if (homodimer.isPresent() && nohomodimer.isPresent() ) { 
 			// This options can't be in the command line at the same time
 			throw new InvalidCommandLineException("/homodimer and /nohomodimer options can not appear in the command line simoultaneously");
		}
		if (homodimerfixedEnd.isPresent() && nohomodimerfixedEnd.isPresent() ) { 
 			// This options can't be in the command line at the same time
 			throw new InvalidCommandLineException("/homodimer3 and /nohomodimer3 options can not appear in the command line simoultaneously");
		}
		if (gccontent.isPresent() && nogccontent.isPresent() ) { 
 			// This options can't be in the command line at the same time
 			throw new InvalidCommandLineException("/gccontent and /nogccontent options can not appear in the command line simoultaneously");
		}
		if (score.isPresent() && noscore.isPresent() ) { 
 			// This options can't be in the command line at the same time
 			throw new InvalidCommandLineException("/score and /noscore options can not appear in the command line simoultaneously");
		}
		if (ampsize.isPresent() && noampsize.isPresent() ) { 
 			// This options can't be in the command line at the same time
 			throw new InvalidCommandLineException("/ampsize and /noampsize options can not appear in the command line simoultaneously");
		}
		if (gccomp.isPresent() && nogccomp.isPresent() ) { 
 			// This options can't be in the command line at the same time
 			throw new InvalidCommandLineException("/gccomp and /nogccomp options can not appear in the command line simoultaneously");
		}
		if (heterodimer.isPresent() && noheterodimer.isPresent() ) { 
 			// This options can't be in the command line at the same time
 			throw new InvalidCommandLineException("/heterodimer and /noheterodimer options can not appear in the command line simoultaneously");
		}
		if (heterodimerfixedEnd.isPresent() && noheterodimerfixedEnd.isPresent() ) { 
 			// This options can't be in the command line at the same time
 			throw new InvalidCommandLineException("/heterodimer3 and /noheterodimer3 options can not appear in the command line simoultaneously");
		}
		if (tmcomp.isPresent() && notmcomp.isPresent() ) { 
 			// This options can't be in the command line at the same time
 			throw new InvalidCommandLineException("/tmcomp and /notmcomp options can not appear in the command line simoultaneously");
		}
		
		if (!pair.isPresent()) {
			boolean isAnyPrimerPairParameter = false;
			isAnyPrimerPairParameter = isAnyPrimerPairParameter || noampsize.isPresent();
			isAnyPrimerPairParameter = isAnyPrimerPairParameter || ampsize.isPresent();
			isAnyPrimerPairParameter = isAnyPrimerPairParameter || smallampsize.isPresent();
			isAnyPrimerPairParameter = isAnyPrimerPairParameter || nogccomp.isPresent();
			isAnyPrimerPairParameter = isAnyPrimerPairParameter || gccomp.isPresent();
			isAnyPrimerPairParameter = isAnyPrimerPairParameter || noheterodimer.isPresent();
			isAnyPrimerPairParameter = isAnyPrimerPairParameter || heterodimer.isPresent();
			isAnyPrimerPairParameter = isAnyPrimerPairParameter || noheterodimerfixedEnd.isPresent();
			isAnyPrimerPairParameter = isAnyPrimerPairParameter || heterodimerfixedEnd.isPresent();
			isAnyPrimerPairParameter = isAnyPrimerPairParameter || notmcomp.isPresent();
			isAnyPrimerPairParameter = isAnyPrimerPairParameter || tmcomp.isPresent();
			
			if (isAnyPrimerPairParameter) {
	 			// there is some options for primer pair search, but /pair option is not present. 
	 			throw new InvalidCommandLineException("/pair option is not present in the command line, but there is one or more parameters for primer pair search.");
	 			
			}
			
		}
		
		/////////////////////////////
		// SET VALUES
		/////////////////////////////

		
		if (infile.isPresent()) this.setInfile((String)infile.getValue());
		if (outfile.isPresent()) this.setOutfile((String)outfile.getValue());
		if (gcfile.isPresent()) this.setGCfile((String)gcfile.getValue());
		
			
		// pass the options of infile, outfile and gcfile
		this.setProfile((String)profile.getValue());
			// if profile is not present, the default value null is passed.
		
		this.setQuantity((Integer)quantity.getValue());
		this.setStartPoint((Integer) start.getValue());
		this.setEndPoint((Integer) end.getValue());
			// pass values for length, starting point, end point and number of primers
		
		this.setDirectStrand(! complementary.isPresent());
			// pass if it is complementary.
		
		this.setDNA(true);
		if (isProtein.isPresent()) this.setDNA(false);
		
		this.setUseSantaLuciaToEstimateTm( true);
		if (tmsimple.isPresent()) this.setUseSantaLuciaToEstimateTm(false);
		TmEstimator tme;
		if (this.isUseSantaLuciaToEstimateTm()) {tme = new SantaluciaTmEstimator();}
		else {tme = new SimpleTmEstimator();}
		
		
		
		this.setNx( (Float) nx.getValue());
		this.setNy( (Float) ny.getValue());
		this.setpA( (Float) pa.getValue());
		
		this.setLenMin( (Integer) lenMin.getValue());
		this.setLenMax( (Integer) lenMax.getValue());

		/////////////////////////////
		// SET VALUES FOR FILTERS
		/////////////////////////////
		
		Validator vf = new ValidateAlways();
		
		List<ValidateForFilterSinglePrimer>    vffsp = new Vector<ValidateForFilterSinglePrimer>(); 
		
		if (filterRep.getValue())              vffsp.add(new ValidateForFilterSinglePrimer(new FilterRepeatedEnd                       ()));
		
		if (filterDeg.getValue())              vffsp.add(new ValidateForFilterSinglePrimer(new FilterDegeneratedEnd                    ()));
		
		if (! noscore.getValue())              vffsp.add(new ValidateForFilterSinglePrimer(new FilterPrimerScore                       ( score.getValue())));		

		if (! notmOpt.isPresent())             vffsp.add(new ValidateForFilterSinglePrimer(new FilterMeltingPointTemperature           (((Float[])tmOpt.getValue())[0], ((Float[])tmOpt.getValue())[1], tme) ));
		
		End5v3Value.Result r = end5v3.getValue();
		if (! noend5v3.isPresent())            vffsp.add(new ValidateForFilterSinglePrimer(new Filter5vs3Stability                     (r.dg, r.ktemp, r.monov, r.len)));
		
		if (! nobaserun.isPresent())           vffsp.add(new ValidateForFilterSinglePrimer(new FilterBaseRuns                          ((Integer) baserun.getValue())));
		
		if (! nohomodimer.isPresent())         vffsp.add(new ValidateForFilterSinglePrimer(new FilterHomoDimer                         ((Integer) homodimer.getValue(), new DegeneratedDNAMatchingStrategy())));
		
		if (! nohomodimerfixedEnd.isPresent()) vffsp.add(new ValidateForFilterSinglePrimer(new FilterHomoDimerFixed3                   ((Integer) homodimerfixedEnd.getValue(), new DegeneratedDNAMatchingStrategy())));
		
		if (! nogccontent.isPresent())         vffsp.add(new ValidateForFilterSinglePrimer(new FilterCGContent                         (((Float[]) gccontent.getValue())[0],((Float[]) gccontent.getValue())[1])));
		
		for (ValidateForFilterSinglePrimer vr : vffsp) {
			vf = new Validate_AND(vf, vr);
		}
		
		this.setFilter(vf);
		
		//////////////////////////////////////////
		// SET VALUES FOR FILTERS OF PRIMER PAIRS 
		//////////////////////////////////////////
		
		if ( pair.isPresent()) {
		
			List<ValidateForFilterPrimerPair>    vffpp = new Vector<ValidateForFilterPrimerPair>();
			Validator vfp = new ValidateAlways(); 		
			
			vffpp.add(new ValidateForFilterPrimerPair(new FilterOverlapping()));
			
			if (! noampsize.isPresent()) vffpp.add(new ValidateForFilterPrimerPair(new FilterAmpliconSize((Integer) ampsize.getValue())));
			
			if (! nosmallampsize.isPresent()) vffpp.add(new ValidateForFilterPrimerPair(new FilterSmallAmpliconSize((Integer) smallampsize.getValue())));
	
			if (! nogccomp.isPresent()) vffpp.add(new ValidateForFilterPrimerPair(new FilterGCCompatibility((Float) gccomp.getValue())));
	
			if (! noheterodimer.isPresent()) vffpp.add(new ValidateForFilterPrimerPair(new FilterHeteroDimer((Integer) heterodimer.getValue(), new DegeneratedDNAMatchingStrategy())));
			
			if (! noheterodimerfixedEnd.isPresent()) vffpp.add(new ValidateForFilterPrimerPair(new FilterHeteroDimerFixed3((Integer) heterodimerfixedEnd.getValue(), new DegeneratedDNAMatchingStrategy())));
			
			if (! notmcomp.isPresent()) vffpp.add(new ValidateForFilterPrimerPair(new FilterMeltingTempCompatibility(tmcomp.getValue(), tme )));
	
			for (ValidateForFilterPrimerPair vr : vffpp) { vfp = new Validate_AND(vfp, vr); }
			
			this.setFilterpair(vfp);

		}
		
		this.setSearchPair( (Boolean) pair.getValue() );
			// Overrides directstrand option.
		
	}
	
	///////////////////////
	// GETTERS && SETTERS
	///////////////////////

	public Optional<String> getInfile() {
		return infile;
	}
	public SearchParameter setInfile(String infile) {
		this.infile = Optional.ofNullable(infile);
		return this;
	}
	public String getOutfile() {
		return outfile;
	}
	public void setOutfile(String outfile) {
		this.outfile = outfile;
	}
	public String getGCfile() {
		return gcfile;
	}
	public void setGCfile(String gcfile) {
		this.gcfile = gcfile;
	}
	public Validator getFilter() {
		return filter;
	}
	public void setFilter(Validator filter) {
		this.filter = filter;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	/**
	 * The first index is One, not Zero.
	 */
	public int getStartPoint() {
		return startPoint;
	}
	/**
	 * The first index is One, not Zero.
	 * @param startPoint
	 */
	public void setStartPoint(int startPoint) {
		this.startPoint = startPoint;
	}
	/**
	 * A value of -1 means the last position of the sequence
	 */
	public int getEndPoint() {
		return endPoint;
	}
	/**
	 * A value of -1 means the last position of the sequence
	 * @param endpoint
	 */
	public void setEndPoint(int endPoint) {
		this.endPoint = endPoint;
	}
	public boolean isDirectStrand() {
		return directStrand;
	}
	public void setDirectStrand(boolean directStrand) {
		this.directStrand = directStrand;
	}
	public boolean isDNA() {
		return  this.isDNA;
	}
	public SearchParameter setDNA(boolean isDNA) {
		this.isDNA = isDNA;
		return this;
	}
	public void setProfile(String profile) {
		this.profile= profile;
	}
	public String getProfile() {
		return this.profile;
	}
	
	public void setNx(float nx) {
		this.Nx = nx;
	}
	public float getNx() {
		return this.Nx;
	}

	public void setNy(float ny) {
		this.Ny = ny;
	}
	public float getNy() {
		return this.Ny;
	}
	
	public void setpA(float pA) {
		this.pA = pA;
	}
	public float getpA() {
		return this.pA;
	}

	public int getLenMin() {
		return lenMin;
	}

	public void setLenMin(int lenMin) {
		this.lenMin = lenMin;
	}

	public int getLenMax() {
		return lenMax;
	}

	public void setLenMax(int lenMax) {
		this.lenMax = lenMax;
	}

	public Validator getFilterpair() {
		return filterpair;
	}

	public void setFilterpair(Validator filterpair) {
		this.filterpair = filterpair;
	}

	public boolean isSearchPair() {
		return searchPair;
	}

	public void setSearchPair(boolean searchPair) {
		this.searchPair = searchPair;
	}

	public boolean isUseSantaLuciaToEstimateTm() {
		return useSantaLuciaToEstimateTm;
	}

	public void setUseSantaLuciaToEstimateTm(boolean useSantaLuciaToEstimateTm) {
		this.useSantaLuciaToEstimateTm = useSantaLuciaToEstimateTm;
	}

	
}
