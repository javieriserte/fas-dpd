package fasdpd.cli;

import java.util.List;
import java.util.Vector;

import fasdpd.SearchParameter;
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
import sequences.util.compare.DegeneratedDNAMatchingStrategy;
import sequences.util.tmcalculator.SantaluciaTmEstimator;
import sequences.util.tmcalculator.SimpleTmEstimator;
import sequences.util.tmcalculator.TmEstimator;

public class SearchParameterBuilder {
	public static SearchParameter getSearchParameter(FASDPDCommandLine cmd) {
		var sp = new SearchParameter();

		// SET VALUES
		if (cmd.infile.isPresent()) sp.setInfile((String)cmd.infile.getValue());
		if (cmd.outfile.isPresent()) sp.setOutfile((String)cmd.outfile.getValue());
		if (cmd.gcfile.isPresent()) sp.setGCfile((String)cmd.gcfile.getValue());

		// pass the options of infile, outfile and gcfile
		sp.setProfile((String)cmd.profile.getValue());
			// if profile is not present, the default value null is passed.
		sp.setQuantity((Integer)cmd.quantity.getValue());
		sp.setStartPoint((Integer) cmd.start.getValue());
		sp.setEndPoint((Integer) cmd.end.getValue());
			// pass values for length, starting point, end point and number of primers
		sp.setDirectStrand(! cmd.complementary.isPresent());
			// pass if it is complementary.
		sp.setDNA(true);
		if (cmd.isProtein.isPresent()) sp.setDNA(false);
		sp.setUseSantaLuciaToEstimateTm( true);
		if (cmd.tmsimple.isPresent()) sp.setUseSantaLuciaToEstimateTm(
		false
    );
		TmEstimator tme;
		if (sp.isUseSantaLuciaToEstimateTm()) {
			tme = new SantaluciaTmEstimator();
		}
		else {tme = new SimpleTmEstimator();}

		sp.setNx( (Float) cmd.nx.getValue());
		sp.setNy( (Float) cmd.ny.getValue());
		sp.setpA( (Float) cmd.pa.getValue());

		sp.setLenMin( (Integer) cmd.lenMin.getValue());
		sp.setLenMax( (Integer) cmd.lenMax.getValue());

		// SET VALUES FOR FILTERS
		Validator vf = new ValidateAlways();
		List<ValidateForFilterSinglePrimer> vffsp =
			new Vector<ValidateForFilterSinglePrimer>();
		if (cmd.filterRep.getValue()) vffsp.add(
			new ValidateForFilterSinglePrimer(
				new FilterRepeatedEnd()));
		if (cmd.filterDeg.getValue()) vffsp.add(
			new ValidateForFilterSinglePrimer(
				new FilterDegeneratedEnd()));
		if (! cmd.noscore.getValue()) vffsp.add(
			new ValidateForFilterSinglePrimer(
				new FilterPrimerScore(cmd.score.getValue())));
		if (! cmd.notmOpt.isPresent()) vffsp.add(
			new ValidateForFilterSinglePrimer(
				new FilterMeltingPointTemperature(
					((Float[])cmd.tmOpt.getValue())[0],
					((Float[])cmd.tmOpt.getValue())[1],
					tme)
				));
		End5v3Value.Result r = cmd.end5v3.getValue();
		if (! cmd.noend5v3.isPresent()) vffsp.add(
			new ValidateForFilterSinglePrimer(
				new Filter5vs3Stability(
					r.dg, r.ktemp, r.monov, r.len)));
		if (! cmd.nobaserun.isPresent()) vffsp.add(
			new ValidateForFilterSinglePrimer(
				new FilterBaseRuns((Integer) cmd.baserun.getValue())));
		if (! cmd.nohomodimer.isPresent()) vffsp.add(
			new ValidateForFilterSinglePrimer(
				new FilterHomoDimer(
					(Integer) cmd.homodimer.getValue(),
					new DegeneratedDNAMatchingStrategy())));
		if (! cmd.nohomodimerfixedEnd.isPresent()) vffsp.add(
			new ValidateForFilterSinglePrimer(
				new FilterHomoDimerFixed3(
					(Integer) cmd.homodimerfixedEnd.getValue(),
					new DegeneratedDNAMatchingStrategy())));
		if (! cmd.nogccontent.isPresent()) vffsp.add(
			new ValidateForFilterSinglePrimer(
				new FilterCGContent(
					((Float[]) cmd.gccontent.getValue())[0],
					((Float[]) cmd.gccontent.getValue())[1])));
		for (ValidateForFilterSinglePrimer vr : vffsp) {
			vf = new Validate_AND(vf, vr);
		}
		sp.setFilter(vf);
		// SET VALUES FOR FILTERS OF PRIMER PAIRS
		if ( cmd.pair.isPresent()) {
			List<ValidateForFilterPrimerPair> vffpp =
				new Vector<ValidateForFilterPrimerPair>();
			Validator vfp = new ValidateAlways();
			vffpp.add(new ValidateForFilterPrimerPair(new FilterOverlapping()));
			if (! cmd.noampsize.isPresent()) vffpp.add(
				new ValidateForFilterPrimerPair(
					new FilterAmpliconSize((Integer) cmd.ampsize.getValue())));
			if (! cmd.nosmallampsize.isPresent()) vffpp.add(
				new ValidateForFilterPrimerPair(
					new FilterSmallAmpliconSize((Integer) cmd.smallampsize.getValue())));
			if (! cmd.nogccomp.isPresent()) vffpp.add(
				new ValidateForFilterPrimerPair(
					new FilterGCCompatibility((Float) cmd.gccomp.getValue())));
			if (! cmd.noheterodimer.isPresent()) vffpp.add(
				new ValidateForFilterPrimerPair(
					new FilterHeteroDimer(
						(Integer) cmd.heterodimer.getValue(),
						new DegeneratedDNAMatchingStrategy())));
			if (! cmd.noheterodimerfixedEnd.isPresent()) vffpp.add(
				new ValidateForFilterPrimerPair(
					new FilterHeteroDimerFixed3(
						(Integer) cmd.heterodimerfixedEnd.getValue(),
						new DegeneratedDNAMatchingStrategy())));
			if (! cmd.notmcomp.isPresent()) vffpp.add(
				new ValidateForFilterPrimerPair(
					new FilterMeltingTempCompatibility(
						cmd.tmcomp.getValue(),
						tme)));

			vfp = vffpp
				.stream()
				.map(a -> (Validator) a)
				.reduce(vfp, (i, j) -> new Validate_AND(j, i));
			sp.setFilterpair(vfp);
		}
		sp.setSearchPair( (Boolean) cmd.pair.getValue() );
    return sp;
  }
}
