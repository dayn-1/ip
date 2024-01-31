package duke.task;

public abstract class Task {
    /** Name or description of given task */
    protected String name;
    /** Denotes if task is completed or not */
    protected boolean isDone;

    public Task(String name) {
        this.name = name;
        this.isDone = false;
    }

    /**
     * Returns alphabetical code for given task
     *
     * @return "E" for Event, "D" for Deadline, "T" for ToDo
     */
    public abstract String getType();

    /**
     * Returns completion status icon for given task
     *
     * @return "X" if completed else " "
     */
    protected String getStatusIcon() {
        return (this.hasCompleted() ? "X" : " ");
    }

    /**
     * Returns boolean completion status for given task
     *
     * @return True if task is completed
     */
    public boolean hasCompleted() {
        return this.isDone;
    }

    /**
     * Marks that task is complete
     */
    public void mark() {
        this.isDone = true;
    }

    /**
     * Unmark the task. Task is not completed.
     */
    public void unmark() {
        this.isDone = false;
    }

    /**
     * Returns task description
     *
     * @return name of task
     */
    public String getDetails() {
        return this.name;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + this.name;
    }
}
