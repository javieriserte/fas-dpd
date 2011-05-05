package sequences.alignment.htmlproducer;

public abstract class HighLight {
	protected String template= "<font color = #1 >#L</font>";			
	protected String templateHigh= "<font color = #1 bgcolor = #2 >#L</font>";;
	protected abstract String highlight(int j, Integer from, Integer to);
	protected String getTemplate() { return this.template; }
	protected String getTemplateHigh() { return this.templateHigh; }
}
