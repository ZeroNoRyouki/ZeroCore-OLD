package zero.mods.zerocore.api.multiblock.rectangular;

/*
 * A multiblock library for making irregularly-shaped multiblock machines
 *
 * Original author: Erogenous Beef
 * https://github.com/erogenousbeef/BeefCore
 *
 * Ported to Minecraft 1.9 by ZeroNoRyouki
 * https://github.com/ZeroNoRyouki/ZeroCore
 */

import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.util.IStringSerializable;

public enum PartPosition  implements IStringSerializable {
	Unknown,
	Interior,
	FrameCorner,
	Frame,
	TopFace,
	BottomFace,
	NorthFace,
	SouthFace,
	EastFace,
	WestFace;
	
	public boolean isFace() {

		switch(this) {

			case TopFace:
			case BottomFace:
			case NorthFace:
			case SouthFace:
			case EastFace:
			case WestFace:
				return true;
			default:
				return false;
		}
	}

	public static PropertyEnum createProperty(String name) {

		return PropertyEnum.create(name, PartPosition.class);
	}


	@Override
	public String getName() {

		return this.toString();
	}
}
