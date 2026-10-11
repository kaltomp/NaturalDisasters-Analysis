package dom2app;

public class SingleDisasterMeasureRequest implements ISingleMeasureRequest {

	private String requestName;
	private String requestFilter;
	private boolean answeredFlag;
	private IMeasurementVector answer;

	public SingleDisasterMeasureRequest() {
		this.answer = new DisasterMeasurementVector();
	}

	public SingleDisasterMeasureRequest(String name, String filter) {
		this.requestName = name;
		this.requestFilter = filter;
		this.answer = new DisasterMeasurementVector();
	}

	@Override
	public String getRequestName() {
		return this.requestName;
	}

	public void setRequestName(String name) {
		this.requestName = name;
	}

	@Override
	public String getRequestFilter() {
		return this.requestFilter;

	}

	public void setRequestFilter(String filter) {
		this.requestFilter = filter;
	}

	@Override
	public boolean isAnsweredFlag() {
		return this.answeredFlag;
	}

	public void setAnsweredFlag(boolean answeredFlag) {
		this.answeredFlag = answeredFlag;
	}

	@Override
	public IMeasurementVector getAnswer() {
		return this.answer;
	}

	public void setAnswer(IMeasurementVector answer) {
		this.answer = answer;
	}

	@Override
	public String getDescriptiveStatsString() {
		return this.answer.getDescriptiveStatsAsString();
	}

	@Override
	public String getRegressionResultString() {
		return this.answer.getRegressionResultAsString();
	}
}
