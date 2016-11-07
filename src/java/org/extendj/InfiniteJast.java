package org.extendj;

import java.io.File;
import java.util.Collection;
import java.util.*;

import org.extendj.ast.CompilationUnit;
import org.extendj.ast.Frontend;
import org.extendj.ast.Problem;
import org.extendj.ast.Program;

import java.io.IOException;
import java.io.PrintStream;

import soot.*;
import soot.options.*;
import soot.options.Options;

public class InfiniteJast extends Frontend {
	
  protected InfiniteJast() {
	  super("InfiniteJast", "0.0.1");
  }
  
  protected void initOptions() {
	  super.initOptions();
	  program.options().addKeyOption("-jimple");
  }

  public static void main(String args[]) {
    int exitCode = new InfiniteJast().run(args);
    if (exitCode != 0) {
      System.exit(exitCode);
    }
  }
  
  public int run(String args[]) {
	  soot.G.reset();
	  soot.Main main = soot.Main.v();
	  
	  //get options for soot
	  ArrayList list = new ArrayList();
	  boolean hasOptionD = program.options().hasValueForOption("-d");
	  String valueForD = hasOptionD ?
		  program.options().getValueForOption("-d") : "." ;
		  
	  list.add("-d");
	  list.add(valueForD);
	  
	  boolean hasClassPath = program.options().hasValueForOption("-classpath");
	  if (hasClassPath) {
		  list.add("-soot-class-path");
		  list.add(program.options().getValueForOption("-classpath"));
	  }
	  
	  //soot parse method takes an array of strings so we need
	  //to convert the list to an array...
	  //TODO: modify soot to take any iterable?
	  String[] argList = new String[list.size()];
	  int index = 0;
	  for(Iterator iter = list.iterator(); iter.hasNext();) {
		  String s = (String)iter.next();
		  argList[index++] = s;
	  }
	  
	  soot.options.Options.v().parse(argList);  
	  
	  Scene.v().loadBasicClasses();
	  Scene.v().loadDynamicClasses();
	  
	  int retval = run(args, Program.defaultBytecodeReader(), Program.defaultJavaParser());
	  	  
	  if (program.options().hasOption("-jimple")) {
		  Options.v().set_output_format(Options.output_format_jimple);
	  }
	  
	  //TODO: Do I still need this? 
	  /*
	  PhaseOptions.v().setPhaseOption("jop", "enabled");
	  PackManager.v().runBodyPacks();
	  PackManager.v().writeOutput();
	  */
	  return retval;
  }

  @Override
  protected int processCompilationUnit(CompilationUnit unit) throws Error {
    // Replace the following super call to skip semantic error checking in unit.
    return super.processCompilationUnit(unit);
  }

  /** Called by processCompilationUnit when there are no errors in the argument unit.  */
  @Override
  protected void processNoErrors(CompilationUnit unit) {
    unit.process();
  }
}
