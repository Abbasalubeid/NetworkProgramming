# HTTPS Feature

This branch introduces HTTPS to the HTTP Guessing Game, enhancing the server with secure communication capabilities. The HTTPS implementation employs SSL/TLS encryption, providing data confidentiality, integrity, and server authentication.

## HTTPS

HTTPS (Hypertext Transfer Protocol Secure) is an extension of HTTP, designed to secure communication over a computer network. In HTTPS, data transfer between the client and server is encrypted using SSL/TLS protocols, safeguarding against eavesdropping and tampering.

## Modifications in `MainServer.java`

The `MainServer` class has been modified to incorporate SSL/TLS features:

- **Keystore Management:** The server uses a keystore file (`keystore.p12`) containing the self-signed SSL certificate.
- **SSL Context and Key Management:** The server sets up an SSL context and initializes it with key managers referencing the keystore, enabling SSL/TLS communication.
- **Listening on SSLServerSocket:** Instead of a standard server socket, the server now listens on an SSLServerSocket, accepting encrypted SSL connections.

## Usage Notes

Access the guessing game via `https://localhost:443` after compiling and running `MainServer.java`. Due to the use of a self-signed certificate, browsers may initially warn about the security risk. You will need to manually trust the certificate for the first access.