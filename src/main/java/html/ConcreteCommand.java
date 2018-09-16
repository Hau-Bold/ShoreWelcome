package html;

import java.io.IOException;

/**
 * the class ConcreteCommand
 *
 */
public class ConcreteCommand implements Command {

	private HtmlExecutor executor;

	/**
	 * Constructor.
	 * 
	 * @param executor
	 *            - the executor
	 */
	public ConcreteCommand(HtmlExecutor executor) {
		this.executor = executor;
	}

	public ConcreteCommand() {
	}

	@Override
	public void execute() {
		try {
			executor.write();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// set & get follows here
	public void setExecutor(HtmlExecutor executor) {
		this.executor = executor;
	}
}
