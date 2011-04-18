package fasdpd;

//import java.util.Arrays;
//import java.util.List;
//import java.util.Vector;

import java.util.List;
import java.util.Vector;

import sequences.util.tmcalculator.SantaluciaTmEstimator;
import sequences.util.tmcalculator.SimpleTmEstimator;
import sequences.util.tmcalculator.TmEstimator;

import cmdGA.MultipleOption;
import cmdGA.NoOption;
import cmdGA.Parser;
import cmdGA.SingleOption;
import cmdGA.exceptions.IncorrectParameterTypeException;
import cmdGA.parameterType.FloatParameter;
import cmdGA.parameterType.IntegerParameter;
import cmdGA.parameterType.StringParameter;
import filters.singlePrimer.Filter5vs3Stability;
import filters.singlePrimer.FilterDegeneratedEnd;
import filters.singlePrimer.FilterMeltingPointTemperature;
import filters.singlePrimer.FilterRepeatedEnd;
import filters.validator.ValidateAlways;
import filters.validator.ValidateForFilterSinglePrimer;
import filters.validator.Validate_AND;
import filters.validator.Validator;

//import fasdpd.tokens.*;



/*
 * You may not change or alter any portion of this comment or credits
 * of supporting developers from this source code or any supporting source code
 * which is considered copyrighted (c) material of the original comment or credit authors.
 * This program is distributed WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * 
 * FAS-DPD project, including algorithms design, software implementation and experimental laboratory work, is being developed as a part of the Research Program:
 * 	"Microbiología molecular básica y aplicaciones biotecnológicas"
 * 		(Basic Molecular Microbiology and biotechnological applications)
 * 
 * And is being conducted in:
 * 	LIGBCM: Laboratorio de Ingeniería Genética y Biología Celular y Molecular.
 *		(Laboratory of Genetic Engineering and Cellular and Molecular Biology)
 *	Universidad Nacional de Quilmes.
 *		(National University Of Quilmes)
 *	Quilmes, Buenos Aires, Argentina.
 *
 * The complete team for this project is formed by:
 *	Lic.  Javier A. Iserte.
 *	Lic.  Betina I. Stephan.
 * 	ph.D. Sandra E. Goñi.
 * 	ph.D. P. Daniel Ghiringhelli.
 *	ph.D. Mario E. Lozano.
 *
 * Corresponding Authors:
 *	Javier A. Iserte. <jiserte@unq.edu.ar>
 *	Mario E. Lozano. <mlozano@unq.edu.ar>
 */
/**
 * This class contains all the parameters search.
 * @author Javier Iserte <jiserte@unq.edu.ar>
 * @version 1.1.3
 */
public class SearchParameter {
	// parameters are stored as instance variables 
	private String infile;
	private String outfile;
	private String gcfile;
	private String profile="";
	private int len;
	private int lenMin;
	private int lenMax;
	private Validator filter= new ValidateAlways();
	private Validator filterpair= new ValidateAlways();
	private int quantity=20;
	private int startPoint=1; // The number of the first position is One, not Zero.
	private int endPoint=-1; // -1 represents the end the sequence.
	private boolean directStrand=true; 
	private boolean isDNA = false;
	private float Nx; 
	private float Ny;
	private float pA;
	
	private boolean searchPair=false;
	private boolean useSantaLuciaToEstimateTm = true;


	// CONSTRUCTOR
	/**
	 * 
	 */
	public SearchParameter() {
		super();
	}

	// Public METHODS
	/**
	 * 
	 */
	public void retrieveFromCommandLine(String[] args) throws InvalidCommandLineException {
	
		Parser parser = new Parser();
		
		SingleOption len = new SingleOption(parser, 20 , "/len", IntegerParameter.getParameter());
		
		SingleOption lenMin = new SingleOption(parser, 20, "/lenMin", IntegerParameter.getParameter());
		SingleOption lenMax = new SingleOption(parser, 25, "/lenMax", IntegerParameter.getParameter());
		
		SingleOption infile = new SingleOption(parser, null , "/infile", StringParameter.getParameter());
		SingleOption outfile = new SingleOption(parser, null , "/outfile", StringParameter.getParameter());
		SingleOption gcfile = new SingleOption(parser, null , "/gcfile", StringParameter.getParameter());
		SingleOption profile = new SingleOption(parser, null , "/profile", StringParameter.getParameter());
		SingleOption quantity = new SingleOption(parser, 20 , "/q", IntegerParameter.getParameter());
		SingleOption start = new SingleOption(parser, 1 , "/startingpoint", IntegerParameter.getParameter());
		SingleOption end = new SingleOption(parser, -1 , "/endpoint", IntegerParameter.getParameter());

		SingleOption nx = new SingleOption(parser, (float)1 , "/nx", FloatParameter.getParameter());
		SingleOption ny = new SingleOption(parser, (float)1 , "/ny", FloatParameter.getParameter());
		SingleOption pa = new SingleOption(parser, (float)0 , "/pa", FloatParameter.getParameter());
		
		NoOption isDna = new NoOption(parser, true , "/isDNA");
		NoOption isProtein = new NoOption(parser, false , "/isProtein");
		
		NoOption complementary = new NoOption(parser, false , "/ComplementaryStrand");
		
		NoOption pair = new NoOption(parser, false, "/pair");
		NoOption tmSL = new NoOption(parser, true, "/tmsantalucia");
		NoOption tmsimple = new NoOption(parser, false, "/tmsimple"); 
		
		// FILTERS
		
		NoOption filterRep = new NoOption(parser, false , "/frep");
		NoOption filterDeg = new NoOption(parser, false , "/fdeg");
		
		SingleOption tm = new SingleOption(parser, new Float[]{50f,65f}, "/tm", FloatArrayParameter.getParameter());
		SingleOption end5v3 = new SingleOption(parser, 1.5, "/end5v3", FloatParameter.getParameter()); // TODO extend a new parameter to store the parameters of end5v3 option
		SingleOption baserun = new SingleOption(parser, 4,"/baserun", IntegerParameter.getParameter());
		// TODO para todos los parámetros que tengan valores por defecto. 
		// Podría hacer una nueva opción que diga si quiere usar ese filtro o no.
		// Es decir que por ejemplo para '/baserun' podrías tener existir un '/nobaserun' que diga que no quiero usar ese filtro.
		// Si lo quiero usar con parámetros default. No lo escribo en la línea de comandos.
		// Si lo quiero usar con mis parámetros, lo escribo en la línea de comandos.
		// Si no lo quiero usar. Escribo en la línea de comandos la opción '/nobaserun'
		SingleOption homodimer = new SingleOption(parser, 5 , "/homodimer", IntegerParameter.getParameter());
		SingleOption homodimerfixedEnd = new SingleOption(parser, 3 , "/homodimer3", IntegerParameter.getParameter());
		SingleOption gccontent = new SingleOption(parser, new Float[]{40f,60f}, "/gc", FloatArrayParameter.getParameter());

		SingleOption ampsize = new SingleOption(parser, 200, "/size", IntegerParameter.getParameter());
		SingleOption gccomp = new SingleOption(parser, 10, "/gccomp", FloatParameter.getParameter());
		SingleOption heterodimer = new SingleOption(parser, 5 , "/hetdimer", IntegerParameter.getParameter());
		SingleOption heterodimerfixedEnd = new SingleOption(parser, 3 , "/hetdimer3", IntegerParameter.getParameter());
		SingleOption tmcomp = new SingleOption(parser, 5, "/tmcomp", FloatParameter.getParameter());
		
		try {
			parser.parseEx(args);
		} catch (IncorrectParameterTypeException e) {
			e.printStackTrace();
		}
		
		// Set Values
		if (! (infile.isPresent()&&outfile.isPresent()&&gcfile.isPresent())) {
			// infile, outfile and gcfile are required!
			// if one of them is not present then the command line is not well formed.
			throw new InvalidCommandLineException("Infile, Outfile and GCfile are required arguments");
		}
		if (infile.isPresent()) this.setInfile((String)infile.getValue());
		if (outfile.isPresent()) this.setOutfile((String)outfile.getValue());
		if (gcfile.isPresent()) this.setGCfile((String)gcfile.getValue());
			// pass the options of infile, outfile and gcfile
 
		this.setProfile((String)profile.getValue());
			// if profile is not present, the default value null is passed.
		
		this.setLen((Integer)len.getValue());
		this.setQuantity((Integer)quantity.getValue());
		this.setStartPoint((Integer) start.getValue());
		this.setEndPoint((Integer) end.getValue());
			// pass values for length, starting point, end point and number of primers
		
		this.setDirectStrand(! complementary.getValue());
			// pass if it is complementary.
		
		if (isDna.isPresent()&&isProtein.isPresent()) {
			// both option can not be in the command line at the same moment.
			throw new InvalidCommandLineException("isDna option and isProtein Option can not be in the command line at the same time.");
		}
		this.setDNA(isDna.getValue());
		if (isProtein.isPresent()) this.setDNA(isProtein.getValue());
		// TODO Check the last line. May be a bug.!!!!!!

		this.setUseSantaLuciaToEstimateTm( tmSL.getValue());
		if (tmsimple.isPresent()) this.setUseSantaLuciaToEstimateTm(false);
		TmEstimator tme;
		if (this.useSantaLuciaToEstimateTm) {tme = new SantaluciaTmEstimator();}
		else {tme = new SimpleTmEstimator();}

		
		Validator v1 = new ValidateAlways();
		Validator v2 = new ValidateAlways();
		
		List<ValidateForFilterSinglePrimer> vffsp = new Vector<ValidateForFilterSinglePrimer>(); 
		
		if (filterRep.getValue()) vffsp.add(new ValidateForFilterSinglePrimer(new FilterRepeatedEnd   () ));
		if (filterDeg.getValue()) vffsp.add(new ValidateForFilterSinglePrimer(new FilterDegeneratedEnd() ));

		if (tmSL.isPresent() && tmsimple.isPresent() ) {
			// These options can not be in the commandline at the same time.
			throw new InvalidCommandLineException("Can not use both Tm estimators at the same time. Choose one.");
		}
		
		vffsp.add(new ValidateForFilterSinglePrimer(new FilterMeltingPointTemperature(((Float[])tm.getValue())[0], ((Float[])tm.getValue())[1], tme) ));
		vffsp.add(new ValidateForFilterSinglePrimer(Filter5vs3Stability.getStandard5vs3StabilityFilter()));
		// TODO para este filtro, debería tener dos opciones. Una, si quiere usar el filtro standard y otra si quiere uno a medida.
		
		this.setFilter(new Validate_AND(v1, v2));
			// creates a filter and passes it.

		this.setNx( (Float) nx.getValue());
		this.setNy( (Float) ny.getValue());
		this.setpA( (Float) pa.getValue());
		
		if((Integer)lenMin.getValue() > (Integer)lenMax.getValue() ) {
			// Primer Min can not be greater than Primer Max.
			throw new InvalidCommandLineException("Max length is lesser than Min length");
		}  		
		this.setLenMin( (Integer) lenMin.getValue());
		this.setLenMax( (Integer) lenMax.getValue());
		
		this.setSearchPair( (Boolean) pair.getValue() );
			// Overrides directstrand option.
		
		
		
		
	}
	
	// GETTERS && SETTERS
	public String getInfile() {
		return infile;
	}
	public void setInfile(String infile) {
		this.infile = infile;
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
	public int getLen() {
		return len;
	}
	public void setLen(int len) {
		this.len = len;
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
	public void setDNA(boolean isDNA) {
		this.isDNA = isDNA;
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
