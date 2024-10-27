export interface LoginResponse {
    code: number;            // The code returned by the API
    result: LoginResult;    // The result object containing token and authenticated status
}

export interface LoginResult {
    token: string;          // The JWT token received
    authenticated: boolean; // Indicates if the user is authenticated
}