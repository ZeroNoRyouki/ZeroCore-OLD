# ZeroCore
Utility mod and multiblock API


This is the last incarnation of my "base/utility" mod for MC 1.9.4 / 1.10.2. It contains code for standard activities such as networking, GUI handling, tile entities synchronization

It also contains the multiblock API at the base of my port of Big Reactor to 1.9.4 / 1.10.2, adapted to work with multiple clients mods. I'm currently working on a tutorial on how to use the API but for the moment you can see a working example in my test mod: https://github.com/ZeroNoRyouki/ZeroTest

The multiblock API is a port and adaptation of the original multiblock API used by Big Reactor form Erogenous Beef. The original code can be found at https://github.com/erogenousbeef/BigReactors

# Usage

Binaries for the mod are available at http://minecraft.curseforge.com/projects/zerocore

A maven repository for both Minecraft1.9.4 and 1.10.2 can be found at http://maven.zerono.it

To add ZeroCore to your project insert the following directives in your build.gradle file:

repositories {
    maven {
        name "zerocore"
        url "http://maven.zerono.it/"
    }
}

dependencies {
    compile group: "it.zerono.mods.zerocore", name: "zerocore", version: "1.10.2-0.0.4"
}

A brief tutorial on how to use the multiblock API is available here: http://zerono.it/zerocore-multiblock-api-tutorial/

# License

I'm not a layer, nor a FOSS fanboy or expert. I like very much the idea behind CoFHCore (https://github.com/CoFH/CoFHCore) "Don't Be a Jerk" license  so I'd like to adopt a similar licence:

# ZeroCore "Don't Be a Jerk" License

This repository does not have a standard license. By default, that means "All Rights Reserved."

That is indeed the case. All rights reserved, as far as the code is concerned.

Contribution to this repository means that you are granting us rights over the code that you choose to contribute. If you do not agree with that, do not contribute.

## You CAN

- Submit Pull Requests to this repository.
- Copy portions of this code for use in other projects.
- Write your own code that uses this code as a dependency. The multiblock library support multiple client mods. You can use ZeroCore as a dependency to build your own multiblocks

## You CANNOT

- Redistribute this in its entirety as source or compiled code.
- Create or distribute code which contains 50% or more Functionally Equivalent Statements* from this repository.

## You MUST

- Maintain a visible repository of your code which is inspired by, derived from, or copied from this code. Basically, if you use it, pay it forward. You keep rights to your OWN code, but you still must make your source visible.
- Not be a jerk**. Seriously, if you're a jerk, you can't use this code. That's part of the agreement.

## Notes, License & Copyright

*A Functionally Equivalent Statement is a code fragment which, regardless of whitespace and object names, achieves the same result. Basically you can't copy the code, rename the variables, add whitespace and say it's different. It's not.

**A jerk is anyone who attempts to or intends to claim partial or total ownership of the original or repackaged code and/or attempts to or intends to redistribute original or repackaged code without prior express written permission from the owners.

Essentially, take this and learn from it! Create addon mods that depend on it! If you see something we can improve, tell us. Submit a Pull Request. The one catch: don't steal! A lot of effort has gone into this, and if you were to take this and call it your own, you'd basically be a big jerk.

Don't be a jerk. 


This license text was adapted from the CoFHCore license available at https://github.com/CoFH/CoFHCore/blob/master/README.md


## License of the multiblock API

The multiblock API is licensed under the MIT license

Please read the LICENSE.md in the api/multiblock package
