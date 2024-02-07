package duke.commands;

import duke.data.exception.DukeException;
import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskList;
import duke.ui.Ui;

/**
 * Command subclass for deleting tasks
 */
public class DeleteCommand extends Command {

    /** Index to perform delete on */
    private final int deleteIndex;

    public DeleteCommand(int deleteIndex) {
        this.deleteIndex = deleteIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        try {
            Task toRemove = tasks.deleteTask(deleteIndex);
            ui.deleteTask(toRemove, tasks.getNumTasks());
            storage.saveTasks(tasks);
        } catch (Exception e) {
            throw new DukeException(e.getMessage());
        }
    }
}
