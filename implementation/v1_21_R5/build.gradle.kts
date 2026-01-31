dependencies {
    implementation(project(":common"))
    compileOnly("org.purpurmc.purpur:purpur-server:1.21.7-R0.1-SNAPSHOT")
	compileOnly("org.jetbrains:annotations:20.1.0")
    compileOnly("com.github.AvarionMC:yaml:1.1.7")
}
java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}