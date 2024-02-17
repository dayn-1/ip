package duke.commands;

import duke.data.exception.DukeException;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

/**
 * Abstract class for executable commands
 */
public abstract class Command {
    private boolean isUnDone;

    /**
     * Performs updates to TaskList, Ui print statements
     * and Storage updates based on the command type
     *
     * @param tasks TaskList of current Duke.
     * @param ui Ui of current Duke.
     * @param storage Storage of current Duke.
     * @throws DukeException  If error executing the command.
     */
    public abstract String execute(TaskList tasks, Ui ui, Storage storage)
            throws DukeException;
    /**
     * Undos the command and perform updates to TaskList,
     * Ui print statements and Storage updates accordingly
     *
     * @param tasks TaskList of current Duke.
     * @param ui Ui of current Duke.
     * @param storage Storage of current Duke.
     * @throws DukeException  If error executing the command.
     */
    public abstract String undo(TaskList tasks, Ui ui, Storage storage)
            throws DukeException;

    /**
     * returns True if ExitCommand type
     */
    public boolean isExit() {
        return false;
    }
    /**
     * returns True if command has been undone before
     */
    public boolean getIsUnDone() {
        return isUnDone;
    }
    protected void setUnDone() {
        this.isUnDone = true;
    }
}
