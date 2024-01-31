package duke.parser;

import duke.commands.Command;
import duke.commands.AddCommand;
import duke.commands.DeleteCommand;
import duke.commands.ExitCommand;
import duke.commands.ListCommand;
import duke.commands.MarkCommand;
import duke.data.exception.DukeException;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.ToDo;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Parser {

	/**
	 * Returns task stored in given line entry.
	 *
	 * @param taskStored String line entry of task stored in txt file
	 * @return ToDo, Event or Deadline Task stored in given line.
	 */
	public static Task parseFromStorage(String taskStored) {
		String[] taskLine = taskStored.split(";;;");
		String[] details = taskLine[2].split(":::");
		Task decodedTask = new ToDo(details[0]);

		if (taskLine[0].equals("D")) decodedTask = new Deadline(details[0], parseStorageDate(details[1]));
		else if (taskLine[0].equals("E")) decodedTask = new Event(details[0],
				parseStorageDate(details[1]), parseStorageDate(details[2]));

		if (taskLine[1].equals("1")) decodedTask.mark();
		return decodedTask;
	}

	/**
	 * Returns command of specified instruction.
	 *
	 * @param fullCmd String full command input by user.
	 * @return Command object.
	 * @throws DukeException  If unknown command was given.
	 */
	public static Command parse(String fullCmd) throws DukeException {
		String cmd = fullCmd.split(" ")[0];
		if (cmd.equals("bye")) return new ExitCommand();
		else if (cmd.equals("list")) return new ListCommand();
		else if (cmd.equals("mark")) return parseMark(fullCmd, true);
		else if (cmd.equals("unmark")) return parseMark(fullCmd, false);
		else if (cmd.equals("todo")) return parseToDo(fullCmd);
		else if (cmd.equals("deadline")) return parseDeadline(fullCmd);
		else if (cmd.equals("event")) return parseEvent(fullCmd);
		else if (cmd.equals("delete")) return parseDelete(fullCmd);
		else throw new DukeException("\tSorry, what do you mean?\n");
	}

	private static MarkCommand parseMark(String fullCmd, boolean isComplete)
			throws DukeException {
		String markFormTxt = "\tSorry! To mark or unmark tasks, please do\n" +
				"\t\t(un)mark (number)\n";
		if (fullCmd.split(" ")[1].trim().isEmpty()) throw new DukeException(markFormTxt);
		int updateIndex = Integer.parseInt(fullCmd.split(" ")[1]);
		return new MarkCommand(updateIndex, isComplete);
	}

	private static AddCommand parseDeadline(String fullCmd) throws DukeException {
		String deadlineFormTxt = "\tSorry! Please use the given format for deadline tasks:\n"
				+ "\t\tdeadline (description) /by DD/MM/YYYY hhmm\n"
				+ "\t\teg. deadline homework /by 30/01/2023 1800\n";

		try {
			String[] fullCmdArr = fullCmd.split("/", 2);
			if (fullCmdArr.length != 2) throw new DukeException(deadlineFormTxt);
			if (!fullCmdArr[1].substring(0, Math.min(3, fullCmdArr[1].length())).equals("by "))
				throw new DukeException(deadlineFormTxt);

			String name = fullCmdArr[0].substring(9);
			String dueDate = fullCmd.split("/", 2)[1].substring(3);
			if (name.trim().isEmpty()) throw new DukeException("Description Blank");
			if (dueDate.trim().isEmpty()) throw new DukeException(deadlineFormTxt);

			Task newTask = new Deadline(name, parseCmdDate(dueDate));
			return new AddCommand(newTask);
		} catch (Exception e) {
			throw new DukeException(deadlineFormTxt);
		}
	}

	private static AddCommand parseEvent(String fullCmd) throws DukeException {
		String eventFormTxt = "\tSorry! Please use the given format for event tasks:\n"
				+ "\t\tevent (description) /from DD/MM/YYYY hhmm /to DD/MM/YYYY hhmm\n"
				+ "\t\teg. event assignment /from 30/01/2023 1200 /to 12/02/2023 2359\n";

		try {
			String[] fullCmdArr = fullCmd.split("/from ");
			if (fullCmdArr.length != 2) throw new DukeException(eventFormTxt);

			String[] fromToArr = fullCmdArr[1].split(" /to ");
			if (fromToArr.length != 2) throw new DukeException(eventFormTxt);

			String name = fullCmdArr[0].substring(6);
			if (name.trim().isEmpty()) throw new DukeException("Description Blank");

			Task newTask = new Event(name, parseCmdDate(fromToArr[0]),
					parseCmdDate(fromToArr[1]));
			return new AddCommand(newTask);
		} catch (Exception e) {
			throw new DukeException(eventFormTxt);
		}
	}

	private static AddCommand parseToDo(String fullCmd) throws DukeException {
		if (fullCmd.length() < 5) throw new DukeException("Description Blank");
		String name = fullCmd.substring(5);
		if (name.trim().isEmpty()) throw new DukeException("Description Blank");
		Task newTask = new ToDo(name);
		return new AddCommand(newTask);
	}

	private static DeleteCommand parseDelete(String fullCmd) throws DukeException {
		String deleteFormTxt = "\tDid you mean to delete the task? Please do this:\n" +
				"\t\tdelete (number)\n";
		try {
			if (fullCmd.split(" ")[1].trim().isEmpty()) throw new DukeException(deleteFormTxt);
			int deleteIndex = Integer.parseInt(fullCmd.split(" ")[1]);
			return new DeleteCommand(deleteIndex);
		} catch (Exception e) {
			throw new DukeException(deleteFormTxt);
		}
	}

	private static LocalDateTime parseCmdDate(String dateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
		return LocalDateTime.parse(dateTime, formatter);
	}

	private static LocalDateTime parseStorageDate(String date) {
		String DATEPATTERN = "yyyy-MM-dd'T'HH:mm";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATEPATTERN);
		return LocalDateTime.parse(date, formatter);
	}


}
