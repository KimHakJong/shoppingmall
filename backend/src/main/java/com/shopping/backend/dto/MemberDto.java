package com.shopping.backend.dto;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 회원가입 요청 DTO
 * - 프론트엔드에서 회원가입 정보를 받을 때 사용
 * - 데이터 검증, 변환, 편의 메서드 포함
 */
public class MemberDto {

    /**
     * 회원가입 요청 데이터
     */
    public static class JoinMemberRequest {
        private String user_id;
        private String user_name;
        private String user_password;
        private String user_email;
        private String cell_tphn;
        private String dtbr; // 생년월일 (YYYYMMDD)
        private String gender;
        private String zip;
        private String bscs_addr;
        private String dtl_addr;
        private String user_role;
        private LocalDateTime created_tsp;
        private LocalDateTime updated_tsp;

        // ===== 생성자 =====
        public JoinMemberRequest() {
            this.created_tsp = LocalDateTime.now();
            this.updated_tsp = LocalDateTime.now();
        }

        public JoinMemberRequest(String user_id, String user_name, String user_password, String user_email,
                                String cell_tphn, String dtbr, String gender, String zip,
                                String bscs_addr, String dtl_addr, String user_role) {
            this.user_id = user_id;
            this.user_name = user_name;
            this.user_password = user_password;
            this.user_email = user_email;
            this.cell_tphn = cell_tphn;
            this.dtbr = dtbr;
            this.gender = gender;
            this.zip = zip;
            this.bscs_addr = bscs_addr;
            this.dtl_addr = dtl_addr;
            this.user_role = user_role;
            this.created_tsp = LocalDateTime.now();
            this.updated_tsp = LocalDateTime.now();
        }

        // ===== Getter/Setter =====
        public String getUser_id() { return user_id; }
        public void setUser_id(String user_id) { this.user_id = user_id; }

        public String getUser_name() { return user_name; }
        public void setUser_name(String user_name) { this.user_name = user_name; }

        public String getUser_password() { return user_password; }
        public void setUser_password(String user_password) { this.user_password = user_password; }

        public String getUser_email() { return user_email; }
        public void setUser_email(String user_email) { this.user_email = user_email; }

        public String getCell_tphn() { return cell_tphn; }
        public void setCell_tphn(String cell_tphn) { this.cell_tphn = cell_tphn; }

        public String getDtbr() { return dtbr; }
        public void setDtbr(String dtbr) { this.dtbr = dtbr; }

        public String getGender() { return gender; }
        public void setGender(String gender) { this.gender = gender; }

        public String getZip() { return zip; }
        public void setZip(String zip) { this.zip = zip; }

        public String getBscs_addr() { return bscs_addr; }
        public void setBscs_addr(String bscs_addr) { this.bscs_addr = bscs_addr; }

        public String getDtl_addr() { return dtl_addr; }
        public void setDtl_addr(String dtl_addr) { this.dtl_addr = dtl_addr; }

        public String getUser_role() { return user_role; }
        public void setUser_role(String user_role) { this.user_role = user_role; }

        public LocalDateTime getCreated_tsp() { return created_tsp; }
        public void setCreated_tsp(LocalDateTime created_tsp) { this.created_tsp = created_tsp; }

        public LocalDateTime getUpdated_tsp() { return updated_tsp; }
        public void setUpdated_tsp(LocalDateTime updated_tsp) { this.updated_tsp = updated_tsp; }

    }
}