package fasdpd;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import filters.validator.ValidateAlways;
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
	private boolean useSantaLuciaToEstimateTm = true;

	public SearchParameter() {
		super();
		strands = new HashSet<StrandSearchDirection>();
		strands.add(StrandSearchDirection.Forward);
	}

	public Set<StrandSearchDirection> getStrands() {
		return this.strands;
	}

	public void addStrand(StrandSearchDirection strand) {
		this.strands.add(strand);
	}

	public void clearStrands() {
		this.strands.clear();
	}

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
	 * @param endPoint
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
