package sequences.alignment.htmlproducer;

@Deprecated
public class WithOutHighLight  extends HighLight {
	@Override protected String highlight(int j, Integer from, Integer to) {
		return this.getTemplate();
	}
}