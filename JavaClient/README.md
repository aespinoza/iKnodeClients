#JClient# would be the class to use to call an iKnode App, e.g.

the Task expression way:
JClient.execute("App:Method", "args");

the Task object way:
Task t = TaskFactory.create("app", "method");
JClient.execute(t, "args");

Task separated args:
JClient.execute("App", "Method", "args");