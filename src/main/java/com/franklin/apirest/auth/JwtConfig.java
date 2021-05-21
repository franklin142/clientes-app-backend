package com.franklin.apirest.auth;
//Almacena configuraciones manuales para JWT en Spring Security
public class JwtConfig {
	//Es la clave secreta para encriptar el token cuando
	//decidimos manejarlo manual en lugar de Spring Security
	//esta clave es para encriptacion basica
	public static final String JWT_KEY ="mi.app.angular";
	//Se usa esta clave para encriptacion fuerte
	public static final String PRIVATE_RSA = "-----BEGIN RSA PRIVATE KEY-----\n"
			+ "MIIEpAIBAAKCAQEAwBfydL009yMjb5Q2yBuT8/r5AeDjtnifJRzKaQBSrWENyWxl\n"
			+ "dXsOoz/cAjpeXhE02SX/+Kr3N9ZlzV3ZRHV3hvp+P64Og+GmrajYow5FscbcdVXl\n"
			+ "c9eTjKFzKzKz589H5b425HUrTid1SpRAO/Pggn4ecXUyxV3ssOg8a8Kn0iFeUcba\n"
			+ "BOW7wmmCVvywwE5hJGY5z2+psc4uPVc9JhewHOVyp332jktHZI5psNZ1K76ojG40\n"
			+ "YLgcihysozew5Pp9AdT0H7UBrULvwiF6P5JanhjAjvF+YXQSHoqtfMG6SY5vB5Eg\n"
			+ "yYN/QgXHSVz7xjlmfNPmpItKc+EoUl+ZBb6nJwIDAQABAoIBACxWmrEVZx9QLgK4\n"
			+ "LDjeHLVpDYbAeO8KQnmXfqTj3TGFwF/otDZ8s/E8cTTWcie2QwOIQOAN3kg2JYNU\n"
			+ "kSy7DF7MfM2ICHOqZwWYfoCMb8vMAeDXdcHaVYVVUrb62gm8j3TZBeSPovJEK0+O\n"
			+ "jpEIBp84KyC3E4P43QQsZp3c7U2T3bgAGcSL3AbiA8luXszUFN4zZnI/06XLu6wU\n"
			+ "VbgN4Wm6Ew8uo1psFo3/oAstpPpNyAarvxG34s9oV8fE2/x+96BlFhAoMV3+I5Q0\n"
			+ "Z3pX8HCZs6fBu6vAU5lQVAgn92Hn8voWIjuc/qHT8P4uYhFGKkoyNtrPcGHsiXHd\n"
			+ "1Hbt2tECgYEA5fi0Evz/aEe05QLfCj5B/AmMMqzearSWlX0sIKzQyS1Jxoielalq\n"
			+ "4bS9RGkJvv6MmaQ8pbUT48U6oMhcnan/WAa3bZasFvWZbSAB4F7NlPLu/g/OcFFw\n"
			+ "ZEdbUu7BsKU8QBnrZiZwHKxUcNUM5CLwGVWNNEQOrKnIMAHgfuGAsQ8CgYEA1dXA\n"
			+ "Pv1hMnMBMs4lzbdTQh6WUsnZsmEPBf6H7CahQ9obbjo50y03bz89VgpBLPrEl5s/\n"
			+ "L132w9t9c1bpBi0sHl/qdb5BKUwFWAOv4NeOJapzL5xfvCUrLByscg2V5urRd08b\n"
			+ "daleDAH5yiyiz0MoV44gwPKQf4HjB2c8o22ZeGkCgYEAsdzDcTgpVX64IrMwy9xQ\n"
			+ "7lx4CS14LDEVkfKosXDXXfDf8WPyuy+30W8XPxS6C/UVyzKiEUtWYcBF0tqD/xDe\n"
			+ "wzcWoPR6jptPGX461UydVcj6HJcfA/GIqpYhxKeSOEvjqHQjgvLRnOuPt2CfwsZe\n"
			+ "yklHl5fXfaUmhjK9bYR9IvMCgYEAj7KR+4J4s9dXAdZIz0KYH8HAUSg2NEpHN0cB\n"
			+ "/m9DRf6nGl7khwZGz2qXBF/lxp1KSXtZbbJeZuX0GvNXqv/mOw9H1xIgZekhWjE4\n"
			+ "GwegDa3KtD8Ikr4DkiQ+unJovwIj+2Jc2KgVfEpDm1p9mnzN+nCXkDIxao5Vi58T\n"
			+ "v9uiG6kCgYA23xUG9khC5oaGtWOFFc0CS+HDZkMndMtmnu3ubfinc8w018ZpnZAk\n"
			+ "7DQbcf1ICbgi7yrAyt0o4hIFz8uPMb1LTD2NwfEwaMymWR93f6lO2QH4MdvCd4LF\n"
			+ "Zea0HpqAisZVcIHEc7xwt/zBWtB0SvKkLXgr1rfLZ0Pb+CB7NxN+Fw==\n"
			+ "-----END RSA PRIVATE KEY-----";
	
	//se usa esta clave para leer el token y poder saber si es valido
	public static final String PUBLIC_RSA = "-----BEGIN PUBLIC KEY-----\n"
			+ "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwBfydL009yMjb5Q2yBuT\n"
			+ "8/r5AeDjtnifJRzKaQBSrWENyWxldXsOoz/cAjpeXhE02SX/+Kr3N9ZlzV3ZRHV3\n"
			+ "hvp+P64Og+GmrajYow5FscbcdVXlc9eTjKFzKzKz589H5b425HUrTid1SpRAO/Pg\n"
			+ "gn4ecXUyxV3ssOg8a8Kn0iFeUcbaBOW7wmmCVvywwE5hJGY5z2+psc4uPVc9Jhew\n"
			+ "HOVyp332jktHZI5psNZ1K76ojG40YLgcihysozew5Pp9AdT0H7UBrULvwiF6P5Ja\n"
			+ "nhjAjvF+YXQSHoqtfMG6SY5vB5EgyYN/QgXHSVz7xjlmfNPmpItKc+EoUl+ZBb6n\n"
			+ "JwIDAQAB\n"
			+ "-----END PUBLIC KEY-----";
}
