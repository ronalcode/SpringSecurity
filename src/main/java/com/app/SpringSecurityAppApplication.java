package com.app;

import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.app.persintence.entities.PermissionEntity;
import com.app.persintence.entities.RoleEntity;
import com.app.persintence.entities.RoleEnum;
import com.app.persintence.entities.UserEntity;
import com.app.persintence.repository.UserRepository;

@SpringBootApplication
public class SpringSecurityAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityAppApplication.class, args);
	}
	
	@Bean
	CommandLineRunner init(UserRepository userRepository)
	{
		return args -> {
			PermissionEntity createPermission =  PermissionEntity.builder()
					.name("CREATE")
					.build();
			PermissionEntity readPermission =  PermissionEntity.builder()
					.name("READ")
					.build();
			PermissionEntity updatePermission =  PermissionEntity.builder()
					.name("UPDATE")
					.build();
			PermissionEntity deletePermission =  PermissionEntity.builder()
					.name("DELETE")
					.build();
			PermissionEntity refactorPermission =  PermissionEntity.builder()
					.name("REFACTOR")
					.build();
			
			/*Create roles */
			RoleEntity roleAdmin = RoleEntity.builder()
					.roleEnum(RoleEnum.ADMIN)
					.permissions(Set.of(createPermission, readPermission,updatePermission,deletePermission))
					.build();
			RoleEntity roleUser = RoleEntity.builder()
					.roleEnum(RoleEnum.USER)
					.permissions(Set.of(createPermission, readPermission))
					.build();
			RoleEntity roleGuest = RoleEntity.builder()
					.roleEnum(RoleEnum.GUEST)
					.permissions(Set.of(readPermission))
					.build();
			RoleEntity roleDeveloper = RoleEntity.builder()
					.roleEnum(RoleEnum.DEVELOPER)
					.permissions(Set.of(createPermission, readPermission,updatePermission,deletePermission,refactorPermission))
					.build();
			
			/** Create users*/
			UserEntity userRonaldo = UserEntity.builder()
					.username("ronaldo")
					.password(new BCryptPasswordEncoder().encode("1234"))
					.isEnabled(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialNoExpired(true)
					.roles(Set.of(roleAdmin))
					.build();
			
			UserEntity userGarcia = UserEntity.builder()
					.username("garcia")
					.password(new BCryptPasswordEncoder().encode("1234"))
					.isEnabled(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialNoExpired(true)
					.roles(Set.of(roleUser))
					.build();
			UserEntity userDaniel = UserEntity.builder()
					.username("daniel")
					.password(new BCryptPasswordEncoder().encode("1234"))
					.isEnabled(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialNoExpired(true)
					.roles(Set.of(roleGuest))
					.build();
			UserEntity userLuis = UserEntity.builder()
					.username("luis")
					.password(new BCryptPasswordEncoder().encode("1234"))
					.isEnabled(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialNoExpired(true)
					.roles(Set.of(roleDeveloper))
					.build();
			
			userRepository.saveAll(List.of(userRonaldo,userGarcia,userDaniel,userLuis));
		};
	}

}
