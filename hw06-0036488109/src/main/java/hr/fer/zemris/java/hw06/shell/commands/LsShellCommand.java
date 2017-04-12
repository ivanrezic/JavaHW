package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.Regexes;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class LsShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments.matches(Regexes.ONE_ARG_QUOTED)) {
			arguments = arguments.substring(1, arguments.length() - 1);
		} else if (!arguments.matches(Regexes.ONE_ARG_NO_QUOTES)) {
			env.writeln("Wrong \"ls\" argument, please check \"help ls\" for usage information.");
			return ShellStatus.CONTINUE;
		}

		ls(env, arguments);
		return ShellStatus.CONTINUE;

	}

	@Override
	public String getCommandName() {
		return "ls";

	}

	@Override
	public List<String> getCommandDescription() {
		return new ArrayList<>(Arrays.asList("temp"));

	}

	private void ls(Environment env, String argument) {
		File directory = new File(argument);
		if (!directory.isDirectory()) {
			env.writeln("Argument should be a directory.");
			return;
		}

		File[] files = directory.listFiles();
		for (File file : files) {
			try {
				outputFile(env, file);
			} catch (IOException e) {
				env.writeln("Ups.. something went wrong with file reading.");
			}
		}
	}

	private void outputFile(Environment env, File file) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Path path = file.toPath();

		BasicFileAttributeView faView = Files.getFileAttributeView(path, BasicFileAttributeView.class,
				LinkOption.NOFOLLOW_LINKS);
		BasicFileAttributes attributes = faView.readAttributes();
		FileTime fileTime = attributes.creationTime();

		String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
		char directory = (Files.isDirectory(path)) ? 'd' : '-';
		char readable = (Files.isReadable(path)) ? 'r' : '-';
		char writable = (Files.isWritable(path)) ? 'w' : '-';
		char executable = (Files.isExecutable(path)) ? 'x' : '-';
		long size = Files.size(path);
		String fileName = path.getFileName().toString();

		String output = String.format("%c%c%c%c %10d %s %s", directory, readable, writable, executable, size,
				formattedDateTime, fileName);

		env.writeln(output);
	}

}
