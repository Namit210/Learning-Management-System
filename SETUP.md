# Setup Instructions

## Prerequisites
- Java 17 or higher
- Maven 3.6+
- MongoDB Atlas account or local MongoDB installation

## Configuration

1. **Copy the example configuration file:**
   ```bash
   cp application.properties.example src/main/resources/application.properties
   ```

2. **Configure your database:**
   - Edit `src/main/resources/application.properties`
   - Replace the MongoDB URI with your actual connection string:
     ```
     spring.data.mongodb.uri=mongodb+srv://<username>:<password>@<cluster>.mongodb.net/LMS?retryWrites=true&w=majority
     ```

3. **Set security secrets:**
   - Generate a secure JWT secret key (minimum 256 bits)
   - Set a strong admin secret key
   - Update these values in `application.properties`:
     ```
     jwt.secret=YOUR_SECURE_JWT_SECRET_KEY_HERE
     app.admin.secret.key=YOUR_ADMIN_SECRET_KEY_HERE
     ```

## Using Environment Variables (Recommended for Production)

Instead of hardcoding values in `application.properties`, you can use environment variables:

```bash
export MONGODB_URI="mongodb+srv://username:password@cluster.mongodb.net/LMS?retryWrites=true&w=majority"
export JWT_SECRET="your-secure-jwt-secret-key"
export JWT_EXPIRATION="86400000"
export ADMIN_SECRET_KEY="your-admin-secret-key"
```

## Running the Application

```bash
# Build the project
./mvnw clean install

# Run the application
./mvnw spring-boot:run
```

The application will start on `http://localhost:8888`

## Important Security Notes

⚠️ **Never commit sensitive information to Git:**
- Database credentials
- JWT secret keys
- Admin secret keys
- API keys

All sensitive configuration is managed through environment variables or local configuration files that are excluded from version control.
