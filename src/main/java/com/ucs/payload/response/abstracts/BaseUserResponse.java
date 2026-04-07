package com.ucs.payload.response.abstracts;

import com.fasterxml.jackson.annotation.JsonView;
import com.ucs.entity.enums.Gender;
import com.ucs.payload.views.Views;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BaseUserResponse {

    private Long id;

    @JsonView(Views.Admin.class)
    private String username;

    @JsonView(Views.Base.class)
    private String name;

    @JsonView(Views.Base.class)
    private String surname;

    @JsonView(Views.Base.class)
    private LocalDate birthDay;

    @JsonView(Views.Admin.class)
    private String ssn;

    @JsonView(Views.Base.class)
    private String birthPlace;

    @JsonView(Views.Base.class)
    private String phoneNumber;

    @JsonView(Views.Base.class)
    private Gender gender;

    @JsonView(Views.User.class)
    private String email;

    @JsonView(Views.Base.class)
    private String userRole;
}

/*Görevler (Birini Seçin):
Çözüm Yolu 1 (Junior Yaklaşımı - Stream Manipulation): StudentResponse sınıfımızın tepesinde zaten çok güçlü bir silahımız var:
@JsonInclude(JsonInclude.Include.NON_NULL). TeacherService içindeki listeleme metoduna giderek,
Mapper'dan dönen veriyi Controller'a iletmeden hemen önce, Stream API veya döngüler yardımıyla hassas alanları nasıl null yaparsınız?

Çözüm Yolu 2 (Mid-Level Yaklaşımı - DTO Pattern): Yukarıdaki çözüm işe yarar ama manuel olduğu için hataya açıktır.
Öğretmenler için BaseUserResponse'u miras almayan, sadece Ad, Soyad, Öğrenci No ve Ders Programı barındıran yepyeni bir SimpleStudentResponse
(veya TeacherViewStudentResponse) DTO'su oluşturun.
Ardından UserMapper içine sadece bu formata çeviri yapan yeni bir metot ekleyip servisi buna göre güncelleyin.

Çözüm Yolu 3 (Senior Araştırma Yaklaşımı - Jackson JsonView): Spring Boot'ta @JsonView anotasyonu nedir?
 Hiç yeni bir DTO sınıfı oluşturmadan, aynı StudentResponse nesnesini kullanarak Manager ve Teacher için
 farklı JSON çıktılarını dinamik olarak nasıl üretebiliriz? Araştırıp uygulayın.*/
