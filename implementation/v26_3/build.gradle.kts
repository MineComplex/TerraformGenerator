dependencies {
    compileOnly(project(":common"))
    compileOnly("cn.dreeam.leaf:leaf-api:26.3.local-SNAPSHOT")
    compileOnly("cn.dreeam.leaf:leaf-server:26.3.local-SNAPSHOT")
	
	compileOnly("org.jetbrains:annotations:20.1.0")
    compileOnly("com.github.AvarionMC:yaml:1.1.7")
}
java {
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
}