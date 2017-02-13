run("8-bit");
setAutoThreshold("Default dark");
setThreshold(1, 255);
//setThreshold(1, 255);
setOption("BlackBackground", false);
run("Convert to Mask");
run("Analyze Particles...", "display");
