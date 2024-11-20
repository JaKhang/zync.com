package com.zync.network.core.persistence;

import com.zync.network.core.domain.ZID;
import org.hibernate.dialect.Dialect;
import org.hibernate.internal.util.BytesHelper;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractClassJavaType;
import org.hibernate.type.descriptor.java.UUIDJavaType;
import org.hibernate.type.descriptor.jdbc.JdbcType;
import org.hibernate.type.descriptor.jdbc.JdbcTypeIndicators;

import java.io.Serializable;
import java.sql.Types;
import java.util.UUID;

public class ZIDJavaType extends AbstractClassJavaType<ZID> {
    protected ZIDJavaType() {
        super(ZID.class);
    }

    public static final ZIDJavaType INSTANCE = new ZIDJavaType();


    public JdbcType getRecommendedJdbcType(JdbcTypeIndicators context) {
        return context.getTypeConfiguration().getJdbcTypeRegistry().getDescriptor(Types.CHAR);
    }

    public String toString(UUID value) {
        return UUIDJavaType.ToStringTransformer.INSTANCE.transform(value);
    }

    public ZID fromString(CharSequence string) {
        return ToStringTransformer.INSTANCE.parse(string.toString());
    }

    public long getDefaultSqlLength(Dialect dialect, JdbcType jdbcType) {
        if (jdbcType.isString()) {
            return ZID.ZID_CHARS;
        } else {
            return jdbcType.isBinary() ? ZID.ZID_BYTES : super.getDefaultSqlLength(dialect, jdbcType);
        }
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public <X> X unwrap(ZID value, Class<X> type, WrapperOptions wrapperOptions) {
        if (value == null) {
            return null;
        } else if (ZID.class.isAssignableFrom(type)) {
            return (X) PassThroughTransformer.INSTANCE.transform(value);
        } else if (String.class.isAssignableFrom(type)) {
            return (X) ToStringTransformer.INSTANCE.transform(value);
        } else if (byte[].class.isAssignableFrom(type)) {
            return (X) ToBytesTransformer.INSTANCE.transform(value);
        } else {
            throw this.unknownUnwrap(type);
        }
    }

    @Override
    public <X> ZID wrap(X value, WrapperOptions wrapperOptions) {
        if (value == null) {
            return null;
        } else if (value instanceof ZID) {
            return PassThroughTransformer.INSTANCE.parse(value);
        } else if (value instanceof String) {
            return ToStringTransformer.INSTANCE.parse(value);
        } else if (value instanceof byte[]) {
            return ToBytesTransformer.INSTANCE.parse(value);
        } else {
            throw this.unknownWrap(value.getClass());
        }
    }

    public static class ToBytesTransformer implements ValueTransformer {
        public static final ToBytesTransformer INSTANCE = new ToBytesTransformer();

        public ToBytesTransformer() {
        }

        public byte[] transform(ZID value) {
            byte[] bytes = new byte[16];

            BytesHelper.fromLong(value.getMostSignificantBits(), bytes, 0);
            BytesHelper.fromLong(value.getLeastSignificantBits(), bytes, 8);
            return bytes;
        }

        public ZID parse(Object value) {
            byte[] bytea = (byte[]) value;
            return new ZID(BytesHelper.asLong(bytea, 0), BytesHelper.asLong(bytea, 8));
        }
    }

    public static class NoDashesStringTransformer implements ValueTransformer {
        public static final NoDashesStringTransformer INSTANCE = new NoDashesStringTransformer();

        public NoDashesStringTransformer() {
        }

        public String transform(ZID ZID) {
            String stringForm = ToStringTransformer.INSTANCE.transform(ZID);
            String var10000 = stringForm.substring(0, 8);
            return var10000 + stringForm.substring(9, 13) + stringForm.substring(14, 18) + stringForm.substring(19, 23) + stringForm.substring(24);
        }

        public ZID parse(Object value) {
            String stringValue = (String) value;
            String var10000 = stringValue.substring(0, 8);
            String ZIDString = var10000 + "-" + stringValue.substring(8, 12) + "-" + stringValue.substring(12, 16) + "-" + stringValue.substring(16, 20) + "-" + stringValue.substring(20);
            return ZID.from(ZIDString);
        }
    }

    public static class ToStringTransformer implements ValueTransformer {
        public static final ToStringTransformer INSTANCE = new ToStringTransformer();

        public ToStringTransformer() {
        }

        public String transform(ZID ZID) {
            return ZID.toString();
        }

        public ZID parse(Object value) {
            return ZID.from((String) value);
        }
    }

    public static class PassThroughTransformer implements ValueTransformer {
        public static final PassThroughTransformer INSTANCE = new PassThroughTransformer();

        public PassThroughTransformer() {
        }

        public ZID transform(ZID ZID) {
            return ZID;
        }

        public ZID parse(Object value) {
            return (ZID) value;
        }
    }

    public interface ValueTransformer {
        Serializable transform(ZID var1);

        ZID parse(Object var1);
    }
}
