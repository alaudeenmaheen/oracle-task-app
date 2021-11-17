db.createUser(
        {
            user: "oracle_user",
            pwd: "Appple@32!",
            roles: [
                {
                    role: "readWrite",
                    db: "tasks"
                }
            ]
        }
);