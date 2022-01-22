package net.bearmine.nso_core.lib.nbtapi.utils.annotations;

import net.bearmine.nso_core.lib.nbtapi.utils.MinecraftVersion;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target({ METHOD })
public @interface AvailableSince {

	MinecraftVersion version();

}