package sequences.alignment.htmlproducer;

@Deprecated
public class WithHighLight extends HighLight {
	protected String bgcolor;

	public WithHighLight(String bgcolor) {
		this.bgcolor = bgcolor;
	}

	@Override
	protected String highlight(int j, Integer from, Integer to) {
		if (j >= from && j <= to) {
			return this.getTemplateHigh().replaceAll("#2", bgcolor);
		}
		return this.getTemplate();
	}
}