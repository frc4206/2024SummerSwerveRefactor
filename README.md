# 2024SummerSwerveRefactor

### Steps to Adding a Dependency

1. Add a dependency in the build.gradle file. E.g.
```
dependencies {
    // include tomlj to parse TOMLs
    implementation 'org.tomlj:tomlj:1.1.1'
}
```

2. Reload the Java language server.
    - `Cntrl` + `Shft` + `P`
    - `>Java: Clean Java Language Server Workspace`
