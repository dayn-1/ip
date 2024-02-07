package duke.commands;

import duke.data.exception.DukeException;
import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskList;
import duke.ui.Ui;

/**
 * Command subclass for marking or unmarking tasks
 */
public class MarkCommand extends Command {

    /** Index to mark or unmark */
    private final int updateIndex;
    /** Denotes whether to mark or unmark task */
    private final boolean isComplete;

    /**
     * Constructor of MarkCommand
     * @param updateIndex task to update
     * @param isComplete whether to mark as done or not done
     */
    public MarkCommand(int updateIndex, boolean isComplete) {
        this.updateIndex = updateIndex;
        this.isComplete = isComplete;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        try {
            Task updateTask = tasks.markTask(updateIndex, isComplete);
            ui.mark(updateTask, isComplete);
            storage.saveTasks(tasks);
        } catch (Exception e) {
            throw new DukeException(e.getMessage());
        }
    }
}
