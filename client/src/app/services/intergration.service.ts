import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginRequest } from '../models/login-request';
import { Observable } from 'rxjs';
import { LoginResponse } from '../models/login-response';

const API_URL = "http://localhost:8080/auth/log-in"

@Injectable({
  providedIn: 'root'
})
export class IntergrationService {

  constructor(private http: HttpClient) { }

  doLogin(request: LoginRequest):Observable<LoginResponse>{
    return this.http.post<LoginResponse>(API_URL, request);
  }
}
