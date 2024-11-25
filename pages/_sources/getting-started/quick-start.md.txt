
# Quick Start

<hr>

**battleaid** is not published on Maven as a public package (yet), and is only as a _GitHub package_.  This requires more setup by the user, but the result functions exactly as any other remote resource.

<hr>

## Steps

1. Generate a GitHub access token (classic) with `read:packages` permission and copy it your clipboard.  [GitHub has a tutorial for this](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens#creating-a-personal-access-token-classic).

```{tip}
Keep your token somewhere safe and easily accessible!  If you decide to use a different computer you will need to repeat these steps!
```

2. Create the file `gradle.properties` in the root directory of your FRC robot project and enter the following into the file (_without_ the angles):
```properties
github.username = <your_github_username_here>
github.token = <your_token_here>
```

3. If it does not already exist, create the file `.gitignore` in your project root directory.  Add `gradle.properties` to the list of entries.

```{warning}
If `gradle.properties` is pushed to the remote repository containing your token (which is basically a password), your token will be publicly exposed.  This puts your account at risk.
```

4. In your FRC robot project in WPILib, add this to the top of your `build.gradle` file:
```
repositories {
    maven {
        url = uri("https://maven.pkg.github.com/frc4206/battleaid")
        credentials {
            username = project.findProperty("github.username") ?: System.getenv("GITHUB_USERNAME")
            password = project.findProperty("github.token") ?: System.getenv("GITHUB_TOKEN")
        }
    }
}
```
5. Add the following to your list of dependencies:
```
dependencies {
    ...
    implementation 'org.team4206:battleaid:<version_number>'
}
```

6. In your terminal, run `./gradlew build`.

> [!TIP]
> If the build is successful but you still see squiggly red errors in your code, you may need to refresh the Java Lanaguage Server in order for **battleaid** to be visible:
> - `Cntrl` + `Shft` + `P`
> - `>Java: Clean Java Language Server Workspace`