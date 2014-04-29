package laboratorie;
import com.martiansoftware.jsap.*;

public class ArgumentsParsing {
	public static void main(String[] args) throws JSAPException {
JSAP jsap = new JSAP();
        FlaggedOption opt1 = new FlaggedOption("output")
                                .setStringParser(JSAP.STRING_PARSER)
                                .setRequired(true) 
                                .setShortFlag('o') 
                                .setLongFlag(JSAP.NO_LONGFLAG);
        
        jsap.registerParameter(opt1);
        
        FlaggedOption opt2 = new FlaggedOption("input")
        .setStringParser(JSAP.STRING_PARSER)
        .setRequired(true) 
        .setShortFlag('i') 
        .setLongFlag(JSAP.NO_LONGFLAG);

jsap.registerParameter(opt2);

FlaggedOption opt3 = new FlaggedOption("width")
.setStringParser(JSAP.STRING_PARSER)
.setRequired(false) 
.setShortFlag('w') 
.setLongFlag(JSAP.NO_LONGFLAG);

jsap.registerParameter(opt3);

FlaggedOption opt4 = new FlaggedOption("height")
.setStringParser(JSAP.STRING_PARSER)
.setRequired(false) 
.setShortFlag('h') 
.setLongFlag(JSAP.NO_LONGFLAG);

jsap.registerParameter(opt4);

FlaggedOption opt5 = new FlaggedOption("keyWords")
.setStringParser(JSAP.STRING_PARSER)
.setRequired(false) 
.setShortFlag('k') 
.setLongFlag(JSAP.NO_LONGFLAG);

jsap.registerParameter(opt5);

        JSAPResult config = jsap.parse(args);
        System.out.println("coucou");
        
        if (!config.success()) {
            System.err.println();
            System.err.println("Usage: java ");
            System.err.println("                "
                                + jsap.getUsage());
            System.err.println();
            // show full help as well
            System.err.println(jsap.getHelp());
            

        }
		
	}

}
