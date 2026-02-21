group = "org.terraform"

dependencies {
    compileOnly("org.apache.commons:commons-lang3:3.20.0")
    compileOnly("cn.dreeam.leaf:leaf-api:1.21.11-R0.1-SNAPSHOT")
    compileOnly("cn.dreeam.leaf:leaf-server:1.21.11-R0.1-SNAPSHOT")
    compileOnly("com.github.AvarionMC:yaml:1.1.7")
}

// Set this to the lowest compat in implementation
java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}