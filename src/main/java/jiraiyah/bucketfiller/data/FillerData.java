package jiraiyah.bucketfiller.data;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.math.BlockPos;

public record FillerData(BlockPos pos)
{
    public static final PacketCodec<ByteBuf, FillerData> PACKET_CODEC =
            PacketCodec.tuple(
                    BlockPos.PACKET_CODEC,
                    FillerData::pos,
                    FillerData::new);
}