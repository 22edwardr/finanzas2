package com.robayo.edward.finances.app.service;

import com.robayo.edward.finances.app.exception.handler.ServiceException;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import org.springframework.stereotype.Service;

import org.springframework.util.FileSystemUtils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import java.net.MalformedURLException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;


@Service
public class UploadFileServiceImpl implements IUploadFileService
{
	private static final String UPLOADS_FOLDER = "uploads";

	@Override
	public Resource load(String filename)
	{
		Path     pathFoto = getPath(filename);
		Resource recurso  = null;

		try
		{
			recurso = new UrlResource(pathFoto.toUri());
		}
		catch(MalformedURLException e)
		{
			throw new RuntimeException("URL mal formada");
		}

		if(!recurso.exists() || !recurso.isReadable())
			throw new RuntimeException("No se puede cargar la imagen" + pathFoto.toString());

		return recurso;
	}

	@Override
	public String copy(MultipartFile file)
	    throws IOException
	{
		String uniqueFilename = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + "_"
			+ file.getOriginalFilename();
		Path   rootPath       = getPath(uniqueFilename);

		Files.copy(file.getInputStream(), rootPath);

		return uniqueFilename;
	}

	@Override
	public boolean delete(String filename)
	{
		Path rootPath = getPath(filename);
		File archivo  = rootPath.toFile();

		if(archivo.exists() && archivo.canRead())
		{
			if(archivo.delete())
				return true;
		}

		return false;
	}

	public Path getPath(String filename)
	{
		return Paths.get(UPLOADS_FOLDER).resolve(filename).toAbsolutePath();
	}

	@Override
	public void deleteAll()
	{
		FileSystemUtils.deleteRecursively(Paths.get(UPLOADS_FOLDER).toFile());
	}

	@Override
	public void init()
	    throws IOException
	{
		Files.createDirectory(Paths.get(UPLOADS_FOLDER));
	}
}
